$(document).ready(function() {

    $.ajaxSetup({
        beforeSend: function(xhr) {
            xhr.setRequestHeader('X-CSRF-TOKEN', $("meta[name='_csrf']").attr("content"));
        }
    });

    var contextUf = $("#contextUf").val();
    var rol = $("#contextRol").val();
    var today = $('#calendar').fullCalendar('getDate');
    var tgl = moment(today).format('YYYY/MM');

    $('#calendar').fullCalendar({
        header: {
            left: 'prev',
            center: 'title,today',
            right: 'next'
        },
        footer: {
            center: 'month,agendaWeek '
        },
        timezone: 'America/Buenos_Aires',
        firstDay: 0,
        fixedWeekCount: false,
        navLinks: false,
        selectable: false,
        contentHeight: 750,
        editable: false,
        eventLimit: true,
        events: function(start, end, timezone, callback) {
            $.ajax({
                url: '/reservas/',
                method: 'POST',
                dataType: 'json',
                contentType: "application/json",
                data: JSON.stringify({
                	fechaMin: toStringDate(start),
                	fechaMax: toStringDate(end)
//                	dateMin: new Date(toStringDate(start)).toJSON(),
//                	dateMax: new Date(toStringDate(end)).toJSON()
                }),
                success: function(doc) {
                    var events = [];
                    $(doc).each(function() {
                        events.push({
                            id: $(this)[0].id,
                            title: $(this)[0].title,
                            // TODO VERIFICAR ESTA DATO
                            start: formatDateForCalendar($(this)[0].date, $(this)[0].start),
                            end: formatDateForCalendar($(this)[0].date, $(this)[0].end)
                        });
                    });
                    callback(events);
                }
            });
        },
        
        displayEventTime: false,
        eventRender: function(event, element) {
            //            element.find('.fc-title').before("UF: ")
            //            	.append("<br/>Desde: ").append(epochToDate(event.start))
            //            	.append("<br/>Hasta: ").append(epochToDate(event.end));

            element.find('.fc-title').before("UF: ")
                .append("<br/>").append(epochToDate(event.start))
                .append(" - ").append(epochToDate(event.end));

            if (moment(event.start) < moment(today)) {
                $(element).addClass("btn btn-default disabled");
                element.css('background-color', '#8c8c8c');
                element.css('border-color', '#8c8c8c');
                element.disable = true;
            } else if (contextUf == event.title) {
                $(element).addClass("btn btn-success");
                element.css('background-color', '#449d44');
                element.css('border-color', '#398439');
            } else {
                $(element).addClass("btn btn-primary");
                element.css('background-color', '#286090');
                element.css('border-color', '#204d74');
            }
        },
        dayRender: function(date, cell) {
            if (moment(date) < moment(today)) {
                cell.css('background-color', '#f2f2f2');
            }
        },
        dayClick: function(date, event, view) {
            if (moment(date) < moment(today)) {
                return false;
            } else {
                loadModal(null, contextUf, toStringDate(date), "18:00", "23:45");
            }
        },
        eventClick: function(event, jsEvent, view) {
            if (moment(event.start) < moment(today)) {
                return;
            } else {
                loadModal(event.id, event.title, toStringDate(event.start), toStringHours(event.start), toStringHours(event.end));
            }
        }
    });


    $('#inicio').timepicker(({
        showSeconds: false,
        showMeridian: false
    }));

    $('#fin').timepicker(({
        showSeconds: false,
        showMeridian: false
    }));

    $('#submitReserva').click(function() {

        var fecha = $('#fechaReserva').val();
        var uf = $('#ufInput').val();
        var inicio = $('#inicio').val();
        var fin = $('#fin').val();

        var eventData;
        eventData = {
            id: 'null',
            title: uf,
            start: new Date(fecha), //+inicio,
            end: new Date(fecha) //+fin,
        };

        $.ajax({
            url: '/reservas/crear',
            data: JSON.stringify(eventData),
            contentType: "application/json",
            dataType: 'json',
            error: function() {
                $('#info').html('<p>An error has occurred</p>');
            },
            success: function(data) {
                eventData.id = data.id;
            },
            type: 'POST'
        });

        $('#calendar').fullCalendar('renderEvent', eventData, true);
        $('#nuevaReserva').modal('hide');
    });

    $('#eliminarReserva').click(function() {

        $.ajax({
            url: '/reservas/eliminar/' + $('#idReserva').val(),
            type: 'DELETE',
            //            contentType: "application/json",
            //            dataType: 'json',
            success: function(data) {
                alert('Reserva eliminada correctamente.');
            },
            error: function() {
                $('#info').html('<p>An error has occurred</p>');
            }
        });

        $('#nuevaReserva').modal('hide');
    });
    
    function loadModal(id, title, fecha, inicio, fin) {

        $('#idReserva').val(id);
        $('#ufInput').val(contextUf);

        if (rol == 'ADMIN' || title == contextUf) {
            $('#eliminarReserva').show(0);
        } else {
            $('#eliminarReserva').hide(0);
        }

        if (rol == 'ADMIN') {
            $('#ufInput').removeClass('disabled');
        } else {
            $('#ufInput').addClass('disabled');
        }

        $('#fechaReserva').val(fecha);
        $('#inicio').val(inicio);
        $('#fin').val(fin);

        $('#nuevaReserva').modal('show');
    }
    

    function formatDateForCalendar(date, hour) {
    	var estaFecha = new Date(date);
    	var hrs = hour.split(':');
    	estaFecha.setHours(hrs[0]);
    	estaFecha.setMinutes(hrs[1]);
    	return estaFecha.toISOString();
    }
});

function epochToDate(epoch) {

    //Create a new JavaScript Date object based on the timestamp
    //multiplied by 1000 so that the argument is in milliseconds, not seconds.
    var date = new Date(epoch);
    //Hours part from the timestamp
    var hours = date.getHours();
    //Minutes part from the timestamp
    var minutes = "0" + date.getMinutes();
    //Seconds part from the timestamp
    var seconds = "0" + date.getSeconds();

    //Will display time in 10:30:23 format
    return hours + ':' + minutes.substr(-2);
}

function getThisDates() {

    var start = $('#calendar').fullCalendar('getView').start;
    var end = $('#calendar').fullCalendar('getView').end;

    return start._d.toISOString() + '/' + end._d.toISOString();

}

function toStringDate(date) {
	return date.format('YYYY')+ '-' + date.format('MM') + '-' + date.format('DD');
}

function toStringHours(date) {
	return date.hour()-3 + ':' + ('0'+date.minutes()).slice(-2);
}
