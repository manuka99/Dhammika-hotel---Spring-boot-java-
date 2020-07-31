function listAllOrder() {

	$.ajax({

		url : "RetrieveOrdersPanel",
		success : function(data, textStatus, jqXHR) {

			var result = $(data).find('#contectChangeArea');
			$('#contectChangeArea').html(result);
			var url = "RetrieveOrdersPanel";
			history.pushState({}, "", url);
			tableRunJquery();

		}

	})

};

function editOrder() {

	var id = "";
	var userID = "";
	$("input:checkbox[type=checkbox]:checked").each(function() {
		id = $(this).val();
		userID = $(this).parent().next('td').next('td').text();

	});

	console.log(id);

	$.ajax({

		url : "RetrieveOrdersPanel",
		data : {
			'orderID' : id,
			'userID' : userID
		},
		success : function(data, textStatus, jqXHR) {

			if (data === "error") {

			} else {
				var result = $(data).find('#orderEditModalDiv');
				$('#orderEditModalDiv').html(result);

				$('#orderEditModal').modal({
					backdrop : 'static',
					keyboard : false
				})
			}

		}

	});
};


function editOrderNotification(OrderID, UserID) {

	var id = OrderID;
	var userID = UserID;
	

	console.log(id);

	$.ajax({

		url : "RetrieveOrdersPanel",
		data : {
			'orderID' : id,
			'userID' : userID
		},
		success : function(data, textStatus, jqXHR) {

			if (data === "error") {

			} else {
				var result = $(data).find('#orderEditModalDiv');
				$('#orderEditModalDiv').html(result);

				$('#orderEditModal').modal({
					backdrop : 'static',
					keyboard : false
				})
			}

		}

	});
};


function updateOrder() {

	var orderID = $('#orderID').val();
	var status = $('#status').val();
	var payment = $('#payment').val();
	var dDate = $('#dDate').val();
	var userID = $('#userID').val();
alert('userID = ' + userID);
	$.ajax({

		url : "updateOrdersPanel",
		data : {
			'orderID' : orderID,
			'status' : status,
			'payment' : payment,
			'dDate' : dDate,
			'userID': userID
		},
		success : function(data, textStatus, jqXHR) {

			if (data === "true")
				$("#body").load(location.href + "#body");

			else
				alert('dwrswraa');

		}

	})
};

function deleteOrders() {

	var ch_list = new Array();
	$("input:checkbox[type=checkbox]:checked").each(function() {
		ch_list.push($(this).val());
	});

	console.log(ch_list);

	$.ajax({

		url : "DeleteOrdersPanel",
		data : {
			'orderIDs[]' : ch_list
		},
		success : function(data, textStatus, jqXHR) {

			if (data === "true")
				$("#body").load(location.href + "#body");

			else
				alert('dwrswraa');

		}

	})

};

function viewUserOrderInvoice() {

	var id = "";
	var userID = "";
	$("input:checkbox[type=checkbox]:checked").each(function() {
		id = $(this).val();
		userID = $(this).parent().next('td').next('td').text();

	});

	console.log(id);

	$.ajax({

		url : "RetrieveOrdersPanel",
		data : {
			'orderID' : id,
			'userID' : userID
		},
		success : function(data, textStatus, jqXHR) {

			if (data === "error") {

			} else {
				var result = $(data).find('#orderInVoiceModalDiv');
				$('#orderInVoiceModalDiv').html(result);

				$('#orderInVoiceModal').modal({
					backdrop : 'static',
					keyboard : false
				})
			}

		}

	});
};
