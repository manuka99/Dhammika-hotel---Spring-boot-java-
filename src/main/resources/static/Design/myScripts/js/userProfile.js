var successComplainAdd = '<div '
		+ ' class="mt-2 mb-4 card alert card alert-success alert-dismissible fade show"'
		+ 'role="alert">' + '<p>'
		+ '<strong>Success: </strong> Your Complain was added successfully.'
		+ '</p>' + '<button type="button" class="close" data-dismiss="alert"'
		+ 'aria-label="Close">' + '<span aria-hidden="true">&times;</span>'
		+ '</button>' + '</div>';

var errorComplainAdd = '<div '
		+ ' class="mt-2 mb-4 card alert card alert-danger alert-dismissible fade show"'
		+ 'role="alert">' + '<p>'
		+ '<strong>Error: </strong> Your Complain was not added.' + '</p>'
		+ '<button type="button" class="close" data-dismiss="alert"'
		+ 'aria-label="Close">' + '<span aria-hidden="true">&times;</span>'
		+ '</button>' + '</div>';

var successResponseAdd = '<div '
		+ ' class="mt-2 mb-4 card alert card alert-success alert-dismissible fade show"'
		+ 'role="alert">' + '<p>'
		+ '<strong>Success: </strong> Your Response was added successfully.'
		+ '</p>' + '<button type="button" class="close" data-dismiss="alert"'
		+ 'aria-label="Close">' + '<span aria-hidden="true">&times;</span>'
		+ '</button>' + '</div>';

var errorResponseAdd = '<div '
		+ ' class="mt-2 mb-4 card alert card alert-danger alert-dismissible fade show"'
		+ 'role="alert">' + '<p>'
		+ '<strong>Error: </strong> Your Response was not added.' + '</p>'
		+ '<button type="button" class="close" data-dismiss="alert"'
		+ 'aria-label="Close">' + '<span aria-hidden="true">&times;</span>'
		+ '</button>' + '</div>';

var connectError = '<div class="mt-2 alert card alert-danger alert-dismissible fade show"'
		+ 'role="alert">'
		+ '<p>'
		+ '<strong>Timeout: </strong>Connection to the server failed.'

		+ '</p>'
		+ '<p>'
		+ 'Please check your internet connection and click here to <strong><a onclick="loadMyProfile()"> refresh.</a></strong>'
		+ '</p>'

		+ '<button type="button" class="close" data-dismiss="alert"'
		+ 'aria-label="Close">'
		+ '<span aria-hidden="true">&times;</span>'
		+ '</button>' + '<div style="height: 10px;"></div>' + '</div>';


var successProfileUpdate = '<div '
	+ ' class="mt-2 mb-4 card alert card alert-success alert-dismissible fade show"'
	+ 'role="alert">' + '<p>'
	+ '<strong>Success: </strong> Your profile was updated successfully.'
	+ '</p>' + '<button type="button" class="close" data-dismiss="alert"'
	+ 'aria-label="Close">' + '<span aria-hidden="true">&times;</span>'
	+ '</button>' + '</div>';

var errorProfileUpdate  = '<div '
	+ ' class="mt-2 mb-4 card alert card alert-danger alert-dismissible fade show"'
	+ 'role="alert">' + '<p>'
	+ '<strong>Error: </strong> Your profile was not updated.' + '</p>'
	+ '<button type="button" class="close" data-dismiss="alert"'
	+ 'aria-label="Close">' + '<span aria-hidden="true">&times;</span>'
	+ '</button>' + '</div>';

var timeoutms = 30000;

var email_static = null;
var fname_static = null;
var lname_static = null;
var phone_static = null;
var dateOfBirth_static = null;
var address_static = null;

address_static = $('#address').val();
email_static = $('#email').val();
fname_static = $('#fname').val();
lname_static = $('#lname').val();
phone_static = $('#phone').val();
dateOfBirth_static = $('#dateOfBirth').val();

$("#updateProfile").hide();

$("#myOrders").click(function() {

	$("#loaderTable").fadeIn();

	console.log("orders in");

	$.ajax({

		url : "/user/orders",
		timeout : timeoutms,
		success : function(data, textStatus, jqXHR) {
			var result = $(data).find('#ContentChange');
			$('#ContentChange').html(result);
			var url = "/user/orders";
			;
			history.pushState({}, "", url);
			ordersTableScriptChanging();
			$("#loaderTable").fadeOut();

		},
		error : function(jqXHR, textStatus, errorThrown) {
			if (textStatus === "timeout") {
				$('#newDataMessages').append(connectError);
				$("#loaderTable").fadeOut();
			}
		}

	})

});

/*
 * my basic details update
 */
$("#myProfile").click(function() {

	loadMyProfile();

});

$("#mySecurity").click(function() {

	$("#loaderTable").fadeIn();
	$('#ContentChange').load('/user/security #ContentChange');
	history.pushState({}, "", "/user/security");
	PasswordSecurityhideAll();
	$("#loaderTable").fadeOut();

});

$("#myComplains").click(function() {

	$("#loaderTable").fadeIn();
	console.log("orders in");

	$.ajax({

		url : "/user/complains",
		timeout : timeoutms,
		success : function(data, textStatus, jqXHR) {
			var result = $(data).find('#ContentChange');
			$('#ContentChange').html(result);
			var url = "/user/complains";
			;
			history.pushState({}, "", url);
			ordersTableScriptChanging();
			$('.file-upload').file_upload();
			$("#loaderTable").fadeOut();
		},
		error : function(jqXHR, textStatus, errorThrown) {
			if (textStatus === "timeout") {
				$('#newDataMessages').append(connectError);
				$("#loaderTable").fadeOut();
			}
		}

	})

});

function loadMyProfile() {

	$("#loaderTable").fadeIn();

	$.ajax({

		url : "/user/profile",
		timeout : timeoutms,
		success : function(data, textStatus, jqXHR) {
			var result = $(data).find('#ContentChange');
			$('#ContentChange').html(result);
			var url = "/user/profile";
			;
			history.pushState({}, "", url);

			address_static = $('#address').val();
			email_static = $('#email').val();
			fname_static = $('#fname').val();
			lname_static = $('#lname').val();
			phone_static = $('#phone').val();
			dateOfBirth_static = $('#dateOfBirth').val();
			$("#updateProfile").hide();
			$("#loaderTable").fadeOut();

		},
		error : function(jqXHR, textStatus, errorThrown) {
			if (textStatus === "timeout") {
				$('#newDataMessages').append(connectError);
				$("#loaderTable").fadeOut();
			}
		}

	})

}

var status = 0;

function inputForm() {

	var fname = $('#fname').val();
	var lname = $('#lname').val();
	var email = $('#email').val();
	var address = $('#address').val();
	var phone = $('#phone').val();
	var dateOfBirth = $('#dateOfBirth').val();
	var atposition = email.indexOf("@");
	var dotposition = email.lastIndexOf(".");

	if (email != email_static && email.length > 10) {

		$.ajax({

			url : "/register/emailCheck",
			data : {
				'email' : email

			},

			success : function(data) {

				if (data === false) {
					status = 1;
					$("#updateProfile").fadeIn();
					console.log(data);
				}

				else {
					status = 0;
					$("#updateProfile").fadeOut();
					alert('email has been taken');
				}

			}

		})

	} else if (fname != fname_static) {

		if (fname.length < 4) {
			status = 0;
			toastr.error('name is too short');
		}

		else {
			status = 1;
			$("#updateProfile").fadeIn();
		}
	} else if (lname != lname_static) {

		console.log("sdsds");

		if (lname.length < 4) {
			status = 0;
			toastr.error('last name is too short');
		}

		else {
			status = 1;
			$("#updateProfile").fadeIn();
		}
	} else if (address != address_static) {

		if (address.length > 12) {
			status = 1;
			$("#updateProfile").fadeIn();

		} else {
			status = 0;
			toastr.error('address is too short');
		}

	}

	else if (phone != phone_static) {
		status = 1;
		$("#updateProfile").fadeIn();
	}

	else if (dateOfBirth != dateOfBirth_static) {

		if (dateOfBirth != "") {
			status = 1;
			$("#updateProfile").fadeIn();
		} else {
			status = 0;
			toastr.error('please select a valide date of birth');
		}
	}

	else {
		status = 0;
		$("#updateProfile").fadeOut();
	}

};

function updateProfile() {

	$("#loaderTable").fadeIn();

	$("#updateProfile").hide();

	event.preventDefault();
	var form = event.target.form; // storing the form

	if (status == 1) {

		var $form = $("#userDetailsBasicForm");

		$.ajax({

			url : "/user/profileUpdate",
			data : $form.serialize(),
			type : "POST",
			timeout : timeoutms,
			success : function(data, textStatus, jqXHR) {

				$("#loaderTable").fadeOut();

				if (data == true) {

					$('#newDataMessages').append(successProfileUpdate);
					$("#headerRefresh22").load(
							location.href + " #headerRefresh22");
					loadMyProfile();
					
				}

				else{
					$('#newDataMessages').append(errorProfileUpdate);
					alert('Your profile was not updated');
				}
			},
			error : function(jqXHR, textStatus, errorThrown) {
				if (textStatus === "timeout") {
					$('#newDataMessages').append(connectError);
					$("#loaderTable").fadeOut();
				}
			}

		})

	}

	else {
		alert('sorry, you dont have access');
	}

}

var newPassword = "";
var currentPassword = "";
var repeatPassword = "";
var status = 0;
var testPassword = /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[^a-zA-Z0-9])(?!.*\s).{8,15}$/;

function PasswordSecurityhideAll() {

	console.log("passSec");
	status = 0;
	$('#newEnter').hide();
	$('#short').hide();
	$('#match').hide();
	$('#conditionPass').hide();
	$("#UpdatePassword").slideUp();
	// $('#newPassword').attr("disabled", "disabled");
	// $('#repeatPass').attr("disabled", "disabled");

}

function passwordUpdateCheck() {

	newPassword = $('#newPassword').val();
	currentPassword = $('#currentPassword').val();
	repeatPassword = $('#repeatPass').val();

	if (currentPassword != "") {

		$('#newPassword').removeAttr('disabled');

		if (newPassword != "") {

			if (newPassword.length > 7) {

				$('#repeatPass').removeAttr('disabled');

				if (newPassword == repeatPassword) {
					PasswordSecurityhideAll();
					status = 1;
					$("#UpdatePassword").slideDown();
				}

				else {
					PasswordSecurityhideAll();
					$('#match').show();

				}

			}

			else {
				PasswordSecurityhideAll();
				$('#repeatPass').attr("disabled", "disabled");
				$('#conditionPass').show();
			}

		} else {
			PasswordSecurityhideAll();
			$('#repeatPass').attr("disabled", "disabled");
			$('#newEnter').show();
		}

	}

	else {
		PasswordSecurityhideAll();
		$('#newPassword').attr("disabled", "disabled");
		$('#repeatPass').attr("disabled", "disabled");
	}

	console.log(status);
};

function updatePassword() {

	$("#loaderTable").fadeIn();

	event.preventDefault();
	var form = event.target.form; // storing the form

	if (status == 1) {
		$
				.ajax({

					url : "/user/passwordUpdate",
					data : {
						'newPass' : newPassword,
						'oldPass' : currentPassword
					},
					type : "POST",
					timeout : timeoutms,
					success : function(data) {
						
						$("#loaderTable").fadeOut();

						if (data == true) {
							toastr
									.success('Success: Your password has been changed!');
							$('#ContentChange').load('/user/security #ContentChange');
						}

						else {
							toastr
									.error('Invalid: Your password has not been changed!');
						}

					},
					error : function(jqXHR, textStatus, errorThrown) {
						if (textStatus === "timeout") {
							$('#newDataMessages').append(connectError);
							$("#loaderTable").fadeOut();
						}
					}

				})
	}

	else
		$.toaster('Please try again latter!', 'Error', 'danger');
};

function addNewInquiryDetails() {

	var subject = $('#subject').val();
	var message = $('#message').val();

	console.log(message + subject);

	var fdbrnn = new FormData();
	fdbrnn.append('subject', subject);
	fdbrnn.append('message', message);

	var fileDatae = document.querySelector('#image1');
	fdbrnn.append('image1', fileDatae.files[0]);

	var fileDatae2 = document.querySelector('#image2');
	fdbrnn.append('image2', fileDatae2.files[0]);

	var fileDatae3 = document.querySelector('#image3');
	fdbrnn.append('image3', fileDatae3.files[0]);

	// for(let [name, value] of fdbrnn) {
	// alert(`${name} = ${value}`); // key1=value1, then key2=value2
	// }

	var request = new XMLHttpRequest();

	request.onreadystatechange = function() {
		if (this.readyState == 4 && this.status == 200) {

			if (this.responseText === "true") {

				$('#newMessages').append(successComplainAdd);
				valueImageUpload = 0;

			}

			else if (this.responseText === "false") {

				$('#newMessages').append(errorComplainAdd);

			}

		}

	};

	request.upload.addEventListener('progress', function(e) {

		document.querySelector('#progress').innerHTML = Math.round(e.loaded
				/ e.total * 100)
				+ '%';

	}, false);

	request.open('post', '/user/addComplain', true);
	request.send(fdbrnn);

};

function MoreOnInquiry(complainID) {

	$("#loaderTable").fadeIn();
	
	$.ajax({

		url : "/user/viewComplain",
		data : {
			'complainID' : complainID
		},
		timeout : timeoutms,
		success : function(data, textStatus, jqXHR) {
			$("#loaderTable").fadeOut();
			var result = $(data).find('#supportEmailPlatForm');
			$('#ContentChange').html(result);
			var url = "/user/viewComplain?complainID=" + complainID;
			history.pushState({}, "", url);
			$('.file-upload').file_upload();
			valueImageUpload = 0;
		},
		error : function(jqXHR, textStatus, errorThrown) {
			if (textStatus === "timeout") {
				$('#newDataMessages').append(connectError);
			}
		}

	})

};

function addResponseINQ() {

	var complainID = $('#complainID').val();
	var message = $('#messagetex').val();

	console.log(message);

	var fdbrnn = new FormData();
	fdbrnn.append('message', message);
	fdbrnn.append('complainID', complainID);

	var fileDatae = document.querySelector('#image1');
	fdbrnn.append('image1', fileDatae.files[0]);

	var fileDatae2 = document.querySelector('#image2');
	fdbrnn.append('image2', fileDatae2.files[0]);

	var fileDatae3 = document.querySelector('#image3');
	fdbrnn.append('image3', fileDatae3.files[0]);

	var request = new XMLHttpRequest();

	request.onreadystatechange = function() {
		if (this.readyState == 4 && this.status == 200) {

			if (this.responseText === "true") {

				$('#newResponses').append(successResponseAdd);
				valueImageUpload = 0;
				$("#inquriyResponsesMsg").load(
						location.href + " #inquriyResponsesMsg");
				$("#formResponse").load(location.href + " #formResponse");
			}

			else if (this.responseText === "false") {

				$('#newResponses').append(errorResponseAdd);

			}
		}
	};

	request.upload.addEventListener('progress', function(e) {

		document.querySelector('#progress').innerHTML = Math.round(e.loaded
				/ e.total * 100)
				+ '%';

	}, false);

	request.open('post', '/user/addComplainResponse', true);
	request.send(fdbrnn);

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

function canceMyOrder(orderID) {

	$
			.ajax({

				url : "CancelOrderUser",
				data : {
					'orderID' : orderID
				},
				timeout : timeoutms,
				success : function(data, textStatus, jqXHR) {

					if (data === 'true')
						alert('order has been cancelled successfully');

					else
						alert('your order cannot be canncelled at this stage for more details please do call our hotline..');
				},
				error : function(jqXHR, textStatus, errorThrown) {
					if (textStatus === "timeout") {
						$('#newDataMessages').append(connectError);
						$("#loaderTable").fadeOut();
					}
				}

			})

};

// orders table
function ordersTableScriptChanging() {
	$(document)
			.ready(
					function() {
						$('#ordersTable').DataTable();
						$('#ordersTable_wrapper').find('label')
								.each(
										function() {
											$(this).parent().append(
													$(this).children());
										});
						$('#ordersTable_wrapper .dataTables_filter').find(
								'input').each(function() {
							const $this = $(this);
							$this.attr("placeholder", "Search");
							$this.removeClass('form-control-sm');
						});
						$('#ordersTable_wrapper .dataTables_length').addClass(
								'd-flex flex-row');
						$('#ordersTable_wrapper .dataTables_filter').addClass(
								'md-form');
						$('#ordersTable_wrapper select')
								.removeClass(
										'custom-select custom-select-sm form-control form-control-sm');
						$('#ordersTable_wrapper select').addClass('mdb-select');
						$('#ordersTable_wrapper .mdb-select').materialSelect();
						$('#ordersTable_wrapper .dataTables_filter').find(
								'label').remove();
					});
}