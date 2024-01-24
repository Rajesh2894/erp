
$(document)
		.ready(
				function() {
					$('.widget-header h2').hide()
					// call to fetch meeting information
					var response = __doAjaxRequest('MeetingCalender.html?fetchMeetingData', 'POST','', false, 'json');
					
					var events = [];

					$.each(response, function(index) {

						let obj = response[index];
						let date = moment(obj.meetingDate).format();
						var desc = obj.meetingPlace;
						
						events.push({
							title : "Place: "+ desc,
							start : date,
							//color : #009933,
		                    display: 'background',
							allDay:false
						});
					});


					$('#calendar')
							.fullCalendar(
									{
										header : {
											left : 'prev,next today',
											center : 'title',
											right : 'month,agendaWeek,agendaDay'
										},
										//defaultView: 'agendaDay',
										editable : true,
										events : events,
										
										//event render function
							            //displaying other information on event element
							            eventRender: function (event, element, view) {
							                /*element.append('<span>' + event.info.eventDescription + '<span><br>');*/
							                

							                
							            },

										// When a day is clicked, summarize all description from all events on that day
										/*dayClick : function(date, allDay,jsEvent, view) {
											
											$('.event-details-table').empty(); 
											$('#calendar').fullCalendar('clientEvents',function(event) {
																if (moment(date).format('YYYY-MM-DD') == moment(event._start).format('YYYY-MM-DD')) {
																	for (var i = 0; i < event.description.length; i++) {
																		// Add details to table
																		$('.event-details-table')
																				.append(
																						'<tr><td class="o-box-name">'
																								+ event.description[i].name
																								+ '</td><td><a href="" class="postpone-del-text">Postpone Delivery</a></td><td><a href="" class="cancel-del-text" data-productQty="'
																								+ event.description[i].product_qty
																								+ '" data-productName="'
																								+ event.description[i].name
																								+ '" data-toggle="modal" data-target="#myModal">Cancel</a></td></tr>');
																	}
																}
															});
										},

										// When event is clicked, summarize all description in that event.
										eventClick : function(calEvent,
												jsEvent, view) {
											function insert(title, product) {
												$(".event-details-table")
														.append(
																'<tr><td class="o-box-name">'
																		+ product
																		+ '</td><td><a href="" class="postpone-del-text">'
																		+ title
																		+ '</a></td><td><a href="" class="cancel-del-text">Cancel</a></td></tr>');
											}
											;

											// Clear list of description
											$(".event-details-table").empty(); 
											for (var i = 0; i < calEvent.description.length; i++) {
												insert(calEvent.title,calEvent.description[i].name);
											}
										}*/
									});

				});
