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
        timezone: 'local',
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
                	min: start,
                	max: end.add(1,'days')
                }),
                success: function(doc) {
                    var events = [];
                    $(doc).each(function() {
                        events.push({
                            id: $(this)[0].id,
                            title: $(this)[0].title,
                            start: $(this)[0].start,
                            end: $(this)[0].end
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
        	if (date < moment().utc().startOf('day')) {
                cell.css('background-color', '#f2f2f2');
            }
        },
        dayClick: function(date, event, view) {
        	if (date < moment().utc().startOf('day')) {
                return false;
            } else {
                loadModal(null, contextUf, date, date);
            }
        },
        eventClick: function(event, jsEvent, view) {
            if (moment(event.start) < moment(today)) {
                return;
            } else {
            	loadModal(event.id, event.title, event.start, event.end);
            }
        }
    });


    $('#horaInicio').timepicker(({
        showSeconds: false,
        explicitMode: true,
        disableMousewheel: false,
        showMeridian: false
    }));

    $('#horaFin').timepicker(({
        showSeconds: false,
        explicitMode: true,
        disableMousewheel: false,
        showMeridian: false
    }));
    
    $('#horaFin').timepicker().on('changeTime.timepicker', function(e) {
    	var fechaReserva = $('#fechaInicio').text();
    	var startTime = moment($('#horaInicio').val(), "HH:mm");
    	var endTime = moment(e.time.value, "HH:mm");
    	var duration = moment.duration(endTime.diff(startTime));
    	
    	if (duration._milliseconds>0) {
    		$('#fechaFin').text(toStringDate(moment(fechaReserva,'DD/MM/YYYY')));
    	} else {
    		$('#fechaFin').text(toStringDate(moment(fechaReserva,'DD/MM/YYYY').add(1,'days')));
    	}
      });

    $('#submitReserva').click(function() {

    	var idRes = $('#idReserva').val();
    	var uf = $('#ufInput').val();
        var fechaInicio = $('#fechaInicio').text();
        var horaInicio = $('#horaInicio').val();
        var fechaFin = $('#fechaFin').text();
        var horaFin = $('#horaFin').val();

        var eventData;
        eventData = {
            id: idRes == '' ? null : idRes,
            title: uf,
            start: formatDateForSubmit(fechaInicio, horaInicio).getTime(),
            end: formatDateForSubmit(fechaFin, horaFin).getTime() 
        };
        
        var am = eventData.id == null ? 'crear' : 'modificar'
        
        $.ajax({
            url: eventData.id == null ? '/reservas/'+am : '/reservas/'+am,
    		type: eventData.id == null ? 'POST' : 'PUT',
            contentType: "application/json",
            dataType: 'json',
            data: JSON.stringify(eventData),
            error: function(err) {
            	$('#errorsInfo').addClass('alert alert-danger alert-dismissible');
            	$('#tituloErrorModal').html('<strong>Ups!</strong>');
            	$('#cuerpoErrorModal').text(err.responseJSON.message);
            	$('#errorsInfo').modal('show');
            },
            success: function(data) {
            	eventData = {
                        id: data.id,
                        title: data.title,
                        start: data.start,
                        end: data.end
                    };
            	$('#calendar').fullCalendar('refetchEvents');

            	$('#info').addClass('alert alert-success alert-dismissible');
                $('#info').html('<strong>Buenísimo!</strong> Tu reserva se ha podido '+am+' correctamente.');
            }
        });

        $('#nuevaReserva').modal('hide');
    });

    $('#eliminarReserva').click(function() {

        $.ajax({
            url: '/reservas/eliminar/' + $('#idReserva').val(),
            type: 'DELETE',
            //            contentType: "application/json",
            //            dataType: 'json',
            success: function(data) {
//                alert('Reserva eliminada correctamente.');
                $('#calendar').fullCalendar('refetchEvents');
                $('#info').addClass('alert alert-success alert-dismissible');
                $('#info').html('<strong>Buenísimo!</strong> Tu reserva se ha eliminado correctamente.');
            },
            error: function() {
            	$('#info').addClass('alert alert-danger alert-dismissible');
                $('#info').html('<strong>ERROR!</strong> Tu reserva no se ha podido eliminar.');
            }
        });

        $('#nuevaReserva').modal('hide');
    });
    
    function loadModal(id, title, inicio, fin) {
    	
//    	if (inicio<today) {
//    		$('#campos').attr('disabled', true);
//    	} else {
//    		$('#campos').attr('disabled', false);
//    	}
    	
    	
    	if (id==null) {
    		$('#tituloModal').text("Nueva Reserva");
    	} else {
    		$('#tituloModal').text("Modificar Reserva");
    	}
    	
        $('#idReserva').val(id);
        
        if (title == null) {
        	$('#ufInput').val(contextUf);	
        } else {
        	$('#ufInput').val(title);
        }
        
        if (rol == 'ADMIN' || title == contextUf) {
            $('#eliminarReserva').show(0);
            $('#submitReserva').show(0);
        } else {
            $('#eliminarReserva').hide(0);
            $('#submitReserva').hide(0);
        }

        if (rol == 'ADMIN') {
            $('#ufInput').removeClass('disabled');
        } else {
            $('#ufInput').addClass('disabled');
        }

        $('#fechaInicio').text(toStringDate(inicio));
        var hora = toStringHours(inicio);
        $('#horaInicio').timepicker('setTime',hora);
        
        $('#fechaFin').text(toStringDate(fin));
        hora = toStringHours(fin);
        $('#horaFin').timepicker('setTime',hora);
        
        $('#nuevaReserva').modal('show');
    }
    

    function formatDateForSubmit(date, hour) {
    	var dateSplit = date.split('/');
    	var hrsSplit = hour.split(':');
    	var finalDate = new Date(dateSplit[2],dateSplit[1]-1,dateSplit[0]);
    	finalDate.setHours(hrsSplit[0]);
    	finalDate.setMinutes(hrsSplit[1]);
    	return finalDate;
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
	return date.format('DD')+'/'+date.format('MM')+'/'+date.format('YYYY') ;
}

function toStringHours(date) {
	return date.format('HH') + ':' + date.format('mm');
}
