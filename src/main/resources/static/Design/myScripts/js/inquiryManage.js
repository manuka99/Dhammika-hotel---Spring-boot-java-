function listAllInquiries() {

	$.ajax({

		url : "RetrieveInquiryPanel",
		success : function(data, textStatus, jqXHR) {

			var result = $(data).find('#contectChangeArea');
			$('#contectChangeArea').html(result);
			var url = "RetrieveInquiryPanel";
			history.pushState({}, "", url);
			tableRunJquery();

		}

	})

};

function respondInquiries() {

	var id = "";
	$("input:checkbox[type=checkbox]:checked").each(function() {
		id = $(this).val();
	});

	console.log(id);

	$.ajax({

		url : "RetrieveInquiryPanel",
		data : {
			'inquiryID' : id,
		},
		success : function(data, textStatus, jqXHR) {

			if (data === "error") {

			} else {
				var result = $(data).find('#contectChangeArea');
				$('#contectChangeArea').html(result);
				var url = "RetrieveInquiryPanel?inquiryID=" + id;
				history.pushState({}, "", url);
				$('.file-upload').file_upload();
				tinyMYCINTSTART();
				startTrackingNewResponses();
			}

		}

	});
};

function deleteInquiries() {

	var ch_list = new Array();
	$("input:checkbox[type=checkbox]:checked").each(function() {
		ch_list.push($(this).val());
	});

	console.log(ch_list);

	$.ajax({

		url : "DeletePanelInquiry",
		data : {
			'iqids[]' : ch_list
		},
		success : function(data, textStatus, jqXHR) {

			if (data === "true")
				$("#body").load(location.href + "#body");

			else
				alert('dwrswraa');

		}

	})

};

function deleteResponsessINQ(resid) {

	$.ajax({

		url : "DeletePanelResponse",
		data : {
			'resid' : resid
		},
		success : function(data, textStatus, jqXHR) {

			if (data === "true")
				$("#body").load(location.href + "#body");

			else
				alert('dwrswraa');

		}

	})

};

function addResponseINQ() {

	var inqID = $('#inqID').val();
	var message = $('#messagetex').val();

	console.log(message);

	var fdbrnn = new FormData();
	fdbrnn.append('message', message);
	fdbrnn.append('inqID', inqID);

	var fileDatae = document.querySelector('#image1');
	fdbrnn.append('img1', fileDatae.files[0]);

	var fileDatae2 = document.querySelector('#image2');
	fdbrnn.append('img2', fileDatae2.files[0]);

	var fileDatae3 = document.querySelector('#image3');
	fdbrnn.append('img3', fileDatae3.files[0]);

	var request = new XMLHttpRequest();

	request.onreadystatechange = function() {
		if (this.readyState == 4 && this.status == 200
				&& this.responseText == "true") {
			$("#body").load(location.href + "#body");
		}
	};

	request.upload.addEventListener('progress', function(e) {

		document.querySelector('#progress').innerHTML = Math.round(e.loaded
				/ e.total * 100)
				+ '%';

	}, false);

	request.open('post', 'InsertResponseINQ', true);
	request.send(fdbrnn);

};

function deleteResponseINQ(responseID) {

	$.ajax({

		url : "DeletePanelResponseDB",
		data : {
			'responseID' : responseID
		},
		success : function(data, textStatus, jqXHR) {

			if (data === "true")
				$("#body").load(location.href + "#body");

			else
				alert('dwrswraa');

		}

	})

};

var valueImageUpload = 0;

$(document).on(
		"click",
		"#uploadNewImage_btn",
		function() {

			if (valueImageUpload == 0) {
				valueImageUpload = 1;
				$('#image1Div').slideDown();
			}

			else if (valueImageUpload == 1
					&& $('#image1').get(0).files.length != 0) {
				valueImageUpload = 2;
				$('#image2Div').slideDown();
			}

			else if (valueImageUpload == 2
					&& $('#image1').get(0).files.length != 0
					&& $('#image2').get(0).files.length != 0) {
				valueImageUpload = 3
				$('#image3Div').slideDown();
				$('#uploadNewImage').slideUp();
			}

			else if (valueImageUpload > 2)
				alert('You cannot have more than 3 images! ');

			else
				alert('Please add images to the empty spaces given! ');

		});


function tinyMYCINTSTART() {

	tinyMCE
			.init({
				selector : '#myTextArea',
				height : 500,
				editor_selector : "mceEditor",
				plugins : 'print preview fullpage powerpaste searchreplace autolink directionality advcode visualblocks visualchars fullscreen image link media template codesample table charmap hr pagebreak nonbreaking anchor toc insertdatetime advlist lists textcolor wordcount tinymcespellchecker a11ychecker imagetools mediaembed  linkchecker contextmenu colorpicker textpattern help',
				toolbar1 : 'formatselect | bold italic strikethrough forecolor backcolor | link | alignleft aligncenter alignright alignjustify  | numlist bullist outdent indent  | removeformat',
				image_advtab : true,
				templates : [ {
					title : 'Test template 1',
					content : 'Test 1'
				}, {
					title : 'Test template 2',
					content : 'Test 2'
				} ],
				content_css : [
						'//fonts.googleapis.com/css?family=Lato:300,300i,400,400i',
						'//www.tinymce.com/css/codepen.min.css' ]
			});

};
