function listDeliveryFee() {

	$.ajax({

		url : "RetrieveDeliveryFeePanel",
		success : function(data, textStatus, jqXHR) {

			var result = $(data).find('#contectChangeArea');
			$('#contectChangeArea').html(result);
			var url = "RetrieveDeliveryFeePanel";
			history.pushState({}, "", url);
			tableRunJquery();

		}

	})

};

function editDeliveryFee() {

	var id = "";
	$("input:checkbox[type=checkbox]:checked").each(function() {
		id = $(this).val();
	});

	console.log(id);

	$.ajax({

		url : "RetrieveDeliveryFeePanel",
		data : {
			'did' : id
		},
		success : function(data, textStatus, jqXHR) {

			if (data === "error") {

			} else {
				var result = $(data).find('#feeEditModalDiv');
				$('#feeEditModalDiv').html(result);

				$('#feeEditModal').modal({
					backdrop : 'static',
					keyboard : false
				})
			}

		}

	});
};

function updateDeliveryFee() {

	var did = $('#feeID').val();
	var total = $('#feeTotal').val();
	var fee = $('#feeFee').val();

	$.ajax({

		url : "updateDeliveryFeePanel",
		data : {
			'did' : did,
			'total' : total,
			'fee' : fee
		},
		success : function(data, textStatus, jqXHR) {

			if (data === "true")
				$("#body").load(location.href + "#body");

			else
				alert('dwrswraa');

		}

	})
};

function deleteDeliveryFee() {

	var ch_list = new Array();
	$("input:checkbox[type=checkbox]:checked").each(function() {
		ch_list.push($(this).val());
	});

	console.log(ch_list);

	$.ajax({

		url : "DeletePanelDeliveryFee",
		data : {
			'dids[]' : ch_list
		},
		success : function(data, textStatus, jqXHR) {

			if (data === "true")
				$("#body").load(location.href + "#body");

			else
				alert('dwrswraa');

		}

	})

};

function AddPanelDeliveryFee() {

	var total = $('#addfeeTotal').val();
	var fee = $('#addfeeFee').val();

	$.ajax({

		url : "AddPanelDeliveryFee",
		data : {
			'total' : total,
			'fee' : fee
		},
		success : function(data, textStatus, jqXHR) {

			if (data === "true")
				$("#body").load(location.href + "#body");

			else
				alert('dwrswraa');

		}

	})
};