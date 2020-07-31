orderChartManagement();
orderChartManagement2();
orderByTypeBar();
ordersCompletedeBar();
ordersProductsSaleBar();

function listAllAboutUs() {

	$.ajax({

		url : "listAllAboutUs",
		success : function(data, textStatus, jqXHR) {

			var result = $(data).find('#contectChangeArea');
			$('#contectChangeArea').html(result);
			var url = "listAllAboutUs";
			history.pushState({}, "", url);
		}

	})

};

function listAllDeveloper() {

	$.ajax({

		url : "listDeveloper",
		success : function(data, textStatus, jqXHR) {

			var result = $(data).find('#contectChangeArea');
			$('#contectChangeArea').html(result);
			var url = "listDeveloper";
			history.pushState({}, "", url);
		}

	})

};

function listAllAdminDashborad() {

	$.ajax({

		url : "PanelDashBoard",
		success : function(data, textStatus, jqXHR) {

			var result = $(data).find('#contectChangeArea');
			$('#contectChangeArea').html(result);
			var url = "PanelDashBoard";
			history.pushState({}, "", url);
			orderChartManagement();
			orderChartManagement2();
			orderByTypeBar();
			ordersCompletedeBar();
			ordersProductsSaleBar();
		}

	})

};

function orderChartManagement() {

	$("#toYear").datepicker({
		format : "yyyy",
		viewMode : "years",
		minViewMode : "years"
	});

	var dataByServlet = null;

	$(function() {
		$('.min-chart#chart-sales').easyPieChart({
			barColor : "#FF5252",
			onStep : function(from, to, percent) {
				$(this.el).find('.percent').text(Math.round(percent));
			}
		});
	});

	var dataYear = $('#toYear').val();

	if (dataYear != '')
		$('#dataRange').text(dataYear);

	$.ajax({

		url : "PanelOrdersChart",
		data : {
			'year' : dataYear
		},
		success : function(dataResponse) {

			var data1 = dataResponse[0];
			var data2 = dataResponse[1];
			var data3 = dataResponse[2];

			var ordersCompleted = dataResponse[3];
			var TotalsalesCompleted = dataResponse[4];
			var totalCompleted = dataResponse[5];

			var ordersCancelled = dataResponse[6];
			var TotalsalesCancelled = dataResponse[7];
			var totalCancelled = dataResponse[8];

			$('#totalValueOfOrders').text("Rs." + data2);
			$('#totalNoOrders').text(data3);

			dataByServlet = data1;

			var ctxL = document.getElementById("lineChart").getContext('2d');
			var myLineChart = new Chart(ctxL, {
				type : 'line',
				data : {
					labels : [ "January", "February", "March", "April", "May",
							"June", "July", "August", "September", "Octomber",
							"November", "December" ],
					datasets : [ {
						label : "Orders Per year based on month",
						fillColor : "#fff",
						backgroundColor : 'rgba(255, 255, 255, .3)',
						borderColor : 'rgba(255, 255, 255)',
						data : dataByServlet,
					} ]
				},
				options : {
					legend : {
						labels : {
							fontColor : "#fff",
						}
					},
					scales : {
						xAxes : [ {
							gridLines : {
								display : true,
								color : "rgba(255,255,255,.25)"
							},
							ticks : {
								fontColor : "#fff",
							},
						} ],
						yAxes : [ {
							display : true,
							gridLines : {
								display : true,
								color : "rgba(255,255,255,.25)"
							},
							ticks : {
								fontColor : "#fff",
							},
						} ],
					}
				}
			});

		}

	});

};

function orderChartManagement2() {

	$("#toYear2").datepicker({
		format : "yyyy",
		viewMode : "years",
		minViewMode : "years"
	});

	var dataYear = $('#toYear2').val();

	$
			.ajax({

				url : "PanelOrdersChart",
				data : {
					'year' : dataYear
				},
				success : function(dataResponse) {

					var data1 = dataResponse[0];
					var data2 = dataResponse[1];
					var data3 = dataResponse[2];

					var ordersCompleted = dataResponse[3];
					var TotalsalesCompleted = dataResponse[4];
					var totalCompleted = dataResponse[5];

					var ordersCancelled = dataResponse[6];
					var TotalsalesCancelled = dataResponse[7];
					var totalCancelled = dataResponse[8];

					console.log(totalCancelled + "dsdsdsdsd");
					
					Chart.defaults.global.defaultFontColor = '#fff';

					// line
					var ctxL = document.getElementById("sales")
							.getContext('2d');
					var myLineChart = new Chart(ctxL, {
						type : 'line',
						data : {
							labels : [ "January", "February", "March", "April",
									"May", "June", "July", "August",
									"September", "Octomber", "November",
									"December" ],
							datasets : [ {
								label : "Orders Completed",
								strokeColor : "rgba(220,220,220,1)",
								pointColor : "rgba(220,220,220,1)",
								pointStrokeColor : "#fff",
								pointHighlightFill : "#fff",
								pointHighlightStroke : "rgba(220,220,220,1)",
								fillColor : "#fff",
								backgroundColor : 'rgba(255, 255, 255, .3)',
								borderColor : 'rgba(255, 255, 255)',
								borderWidth : 1,
								data : ordersCompleted,
							}, {
								label : "Orders Cancelled",
								strokeColor : "rgba(151,187,205,1)",
								pointColor : "rgba(151,187,205,1)",
								pointStrokeColor : "#fff",
								pointHighlightFill : "#fff",
								pointHighlightStroke : "rgba(151,187,205,1)",
								fillColor : "#fff",
								backgroundColor : 'rgba(255, 255, 255, .3)',
								borderColor : 'rgba(255, 255, 255)',
								borderWidth : 1,
								data : ordersCancelled,
							} ]
						},
						options : {
							responsive : true
						}
					});

					var s = '<tbody id="ordwersColumns" >'
							+ '<tr class="none-top-border" id="ordwersCompletedColumn" ><td>Orders Completed</td>';

					for (var i = 0; i < ordersCompleted.length; i++) {

						s += '<td>' + ordersCompleted[i] + '</td>';

					}
					;

					s += '</tr><tr id="ordwersCancelledColumn" ><td>Orders Cancelled</td>';

					for (var i = 0; i < ordersCancelled.length; i++) {

						s += '<td>' + ordersCancelled[i] + '</td>';

					}
					;

					s += '</tr></tbody>';

					document.getElementById('ordwersColumns').innerHTML = s;

					$('#totalCSales').text(" Rs." + TotalsalesCompleted);
					$('#totalCorders').text(" " + totalCompleted);
					$('#totalCancellSales').text(" Rs." + TotalsalesCancelled);
					$('#totalCancellorders').text(" " + totalCancelled);

				}

			});

};

// bar
function orderByTypeBar() {
	
	var dataYear = $('#toYear3').val();
	var dataMonth = $('#toMonth3').val();

	$.ajax({

		url : "PanelOrdersChart",
		data : {
			'year' : dataYear,
			'month' : dataMonth
		},
		success : function(dataResponse) {

			var data1 = dataResponse[0];
			var data2 = dataResponse[1];
			var data3 = dataResponse[2];

			var ordersCompleted = dataResponse[3];
			var TotalsalesCompleted = dataResponse[4];
			var totalCompleted = dataResponse[5];

			var ordersCancelled = dataResponse[6];
			var TotalsalesCancelled = dataResponse[7];
			var totalCancelled = dataResponse[8];

			var orderType = dataResponse[9];

			var ctxB = document.getElementById("traffic").getContext('2d');
			var myBarChart = new Chart(ctxB, {
				type : 'bar',
				data : {
					labels : [ "Paypal", "Payhere", "Cash on Delivery" ],
					datasets : [ {
						label : 'from orders placed',
						data : orderType,
						backgroundColor : [ 'rgba(255, 255, 255, 0.3)',
								'rgba(255, 255, 255, 0.3)',
								'rgba(255, 255, 255, 0.3)',
								'rgba(255, 255, 255, 0.3)',
								'rgba(255, 255, 255, 0.3)',
								'rgba(255, 255, 255, 0.3)' ],
						borderColor : [ 'rgba(255, 255, 255, 1)',
								'rgba(255, 255, 255, 1)',
								'rgba(255, 255, 255, 1)',
								'rgba(255, 255, 255, 1)',
								'rgba(255, 255, 255, 1)',
								'rgba(255, 255, 255, 1)' ],
						borderWidth : 1
					} ]
				},
				options : {
					legend : {
						labels : {
							fontColor : "white"
						}
					},
					scales : {
						yAxes : [ {
							ticks : {
								beginAtZero : true,
								fontColor : "white"
							}
						} ],
						xAxes : [ {
							ticks : {
								fontColor : "white"
							}
						} ]
					}
				}
			});

		}

	});

};


function ordersCompletedeBar() {
	
	var dataYear = $('#toYear4').val();
	var dataMonth = $('#toMonth4').val();

	$.ajax({

		url : "PanelOrdersChart",
		data : {
			'year' : dataYear,
			'month' : dataMonth
		},
		success : function(dataResponse) {

			var data1 = dataResponse[0];
			var data2 = dataResponse[1];
			var data3 = dataResponse[2];

			var ordersCompleted = dataResponse[3];
			var TotalsalesCompleted = dataResponse[4];
			var totalCompleted = dataResponse[5];

			var ordersCancelled = dataResponse[6];
			var TotalsalesCancelled = dataResponse[7];
			var totalCancelled = dataResponse[8];

			var orderType = dataResponse[9];
			var orderTypeCompleted = dataResponse[10];

			var ctxB = document.getElementById("ordercom").getContext('2d');
			var myBarChart = new Chart(ctxB, {
				type : 'bar',
				data : {
					labels : [ "Paypal", "Payhere", "Cash on Delivery" ],
					datasets : [ {
						label : 'from orders completed',
						data : orderTypeCompleted,
						backgroundColor : [ 'rgba(255, 255, 255, 0.3)',
								'rgba(255, 255, 255, 0.3)',
								'rgba(255, 255, 255, 0.3)',
								'rgba(255, 255, 255, 0.3)',
								'rgba(255, 255, 255, 0.3)',
								'rgba(255, 255, 255, 0.3)' ],
						borderColor : [ 'rgba(255, 255, 255, 1)',
								'rgba(255, 255, 255, 1)',
								'rgba(255, 255, 255, 1)',
								'rgba(255, 255, 255, 1)',
								'rgba(255, 255, 255, 1)',
								'rgba(255, 255, 255, 1)' ],
						borderWidth : 1
					} ]
				},
				options : {
					legend : {
						labels : {
							fontColor : "white"
						}
					},
					scales : {
						yAxes : [ {
							ticks : {
								beginAtZero : true,
								fontColor : "white"
							}
						} ],
						xAxes : [ {
							ticks : {
								fontColor : "white"
							}
						} ]
					}
				}
			});

		}

	});

};

function ordersProductsSaleBar() {
	
	var dataYear = $('#toYear5').val();
	var dataMonth = $('#toMonth5').val();

	$.ajax({

		url : "PanelOrdersChartProduct",
		data : {
			'year' : dataYear,
			'month' : dataMonth
		},
		success : function(dataResponse) {

			var data1 = dataResponse[0];
			var data2 = dataResponse[1];

			var ctxB = document.getElementById("orderProduct").getContext('2d');
			var myBarChart = new Chart(ctxB, {
				type : 'bar',
				data : {
					labels : data1,
					datasets : [ {
						label : 'Find the famous product',
						data : data2,
						backgroundColor : 'rgba(255, 255, 255, .3)',
						borderColor : 'rgba(255, 255, 255)',
						borderWidth : 1
					} ]
				},
				options : {
					legend : {
						labels : {
							fontColor : "white"
						}
					},
					scales : {
						yAxes : [ {
							ticks : {
								beginAtZero : true,
								fontColor : "white"
							}
						} ],
						xAxes : [ {
							ticks : {
								fontColor : "white"
							}
						} ]
					}
				}
			});

		}

	});

};