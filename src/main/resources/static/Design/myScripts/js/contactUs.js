function listAllContactUs() {

	$.ajax({

		url : "RetrieveContactUsPanel",
		success : function(data, textStatus, jqXHR) {

			var result = $(data).find('#contectChangeArea');
			$('#contectChangeArea').html(result);
			var url = "RetrieveContactUsPanel";
			history.pushState({}, "", url);
			tableRunJquery();

		}

	})

};

function respondContactUs() {

	var id = "";
	$("input:checkbox[type=checkbox]:checked").each(function() {
		id = $(this).val();
	});

	console.log(id);

	$.ajax({

		url : "RespondContactUsPanel",
		data : {
			'cuid' : id,
		},
		success : function(data, textStatus, jqXHR) {

			if (data === "error") {

			} else {
				var result = $(data).find('#contectChangeArea');
				$('#contectChangeArea').html(result);
				var url = "RespondContactUsPanel?cuid=" + id;
				history.pushState({}, "", url);
				tinyMYCINTSTART();
				$("#body").load(location.href + "#body");
			}

		}

	});
};

function deleteContactUs() {

	var ch_list = new Array();
	$("input:checkbox[type=checkbox]:checked").each(function() {
		ch_list.push($(this).val());
	});

	console.log(ch_list);

	$.ajax({

		url : "DeletePanelContactUs",
		data : {
			'cuids[]' : ch_list
		},
		success : function(data, textStatus, jqXHR) {

			if (data === "true")
				$("#body").load(location.href + "#body");

			else
				alert('dwrswraa');

		}

	})

};

function addResponse() {
	
	alert(tinyMCE.get('myTextArea').getContent());

	var message = tinymce.activeEditor.getContent();
	var contactUsID = $('#contactUsID').val();

	console.log(message);

	$.ajax({

		url : "InsertResponseForm",
		data : {
			'message' : message,
			'contactUsID' : contactUsID
		},
		success : function(data, textStatus, jqXHR) {

			if (data === "true")
				$("#body").load(location.href + "#body");

			else
				alert('dwrswraa');

		}

	})

};

function deleteResponse(responseID) {

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

function tinyMYCINTSTART() {
	
	tinyMCE.init({
		  selector: '#myTextArea',
		  height: 500,
		  editor_selector : "mceEditor" ,
		  plugins: 'print preview fullpage powerpaste searchreplace autolink directionality advcode visualblocks visualchars fullscreen image link media template codesample table charmap hr pagebreak nonbreaking anchor toc insertdatetime advlist lists textcolor wordcount tinymcespellchecker a11ychecker imagetools mediaembed  linkchecker contextmenu colorpicker textpattern help',
		  toolbar1: 'formatselect | bold italic strikethrough forecolor backcolor | link | alignleft aligncenter alignright alignjustify  | numlist bullist outdent indent  | removeformat',
		  image_advtab: true,
		  templates: [
		    { title: 'Test template 1', content: 'Test 1' },
		    { title: 'Test template 2', content: 'Test 2' }
		  ],
		  content_css: [
		    '//fonts.googleapis.com/css?family=Lato:300,300i,400,400i',
		    '//www.tinymce.com/css/codepen.min.css'
		  ]
		 });



};
