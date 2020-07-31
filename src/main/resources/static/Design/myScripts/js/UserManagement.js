function listUsersByRoleID(roleID) {

	console.log("asasasffgfgguj");

	$.ajax({

		url : "RetreivePanelUsers",
		data : {
			'roleID' : roleID

		},
		success : function(data, textStatus, jqXHR) {

			var result = $(data).find('#contectChangeArea');
			$('#contectChangeArea').html(result);
			var url = "RetreivePanelUsers?roleID=" + roleID;
			history.pushState({}, "", url);
			tableRunJquery();

		}

	})
};

function deleteUsersbyIDs() {
	var ch_list = new Array();
	$("input:checkbox[name='items']:checked").each(function() {
		ch_list.push($(this).val());
	});

	console.log(ch_list);

	$.ajax({

		url : "DeletePanelUsers",
		data : {
			'uids[]' : ch_list
		},
		success : function(data, textStatus, jqXHR) {

			if (data === "success")
				$("#body").load(location.href + "#body");

			else
				alert('dwrswraa');

		}

	})

};

var UserID = "";
var userStatus = false;

function editUser() {

	console.log("sasasasawqqqq");

	var id = "";
	$("input:checkbox[name='items']:checked").each(function() {
		id = $(this).val();
	});

	console.log(id);

	$.ajax({

		url : "RetreivePanelUsers",
		data : {
			'userID' : id
		},
		success : function(data, textStatus, jqXHR) {

			if (data == "error") {

			} else {
				var result = $(data).find('#UserEditModalDiv');
				$('#UserEditModalDiv').html(result);
				UserID = $('#editUserID').val();
				userStatus = $('#getEditUserStatus').val();
				// CatergoryID = $('#getEditUserStatus').val();

				console.log(userStatus);

				$('#editUserOption option[value=' + userStatus + ']').attr(
						'selected', 'selected');

				// $('#productCategory option[value=' + CatergoryID +
				// ']').attr('selected', 'selected');

				// console.log(productIDOnload);
				$('#UserEditModal').modal({
					backdrop : 'static',
					keyboard : false
				})
			}

		}

	});

};

function updateUserDetails() {

	var userID = $('#editUserID').val();
	var name = $('#editUserName').val();
	var email = $('#editUserEmail').val();
	var phone = $('#editUserPhone').val();
	var dateOfBirth = $('#editUserDOB').val();
	var address = $('#editUserAddress').val();
	var status = $("#editUserSelect option:selected").val();

	var roleIDList = new Array();
	$("input:checkbox[name='roles']:checked").each(function() {
		roleIDList.push($(this).val());
	});

	$.ajax({

		url : "UpdatePanelUsers",
		data : {
			'userID' : userID,
			'name' : name,
			'email' : email,
			'phone' : phone,
			'dateOfBirth' : dateOfBirth,
			'address' : address,
			'status' : status,
			'roles' : roleIDList,

		},
		success : function(data, textStatus, jqXHR) {

			if (data === "success")
				$("#body").load(location.href + "#body");

			else
				alert('dwrswraa');

		}

	})
};

function updateUserPassword() {

	var userID = $('#editUserID').val();

	$.ajax({

		url : "updatePanelUserPassword",
		data : {
			'userID' : userID,

		},
		success : function(data, textStatus, jqXHR) {

			if (data === "true")
				alert('password was changed!');

			else
				alert('dwrswraa');

		}

	})
};

function addnewUserModelOpen() {

	$.ajax({

		url : "RetreivePanelUsers",
		success : function(data, textStatus, jqXHR) {

			var result = $(data).find('#UserAddModalDiv');
			$('#UserAddModalDiv').html(result);

			$('#UserAddModal').modal({
				backdrop : 'static',
				keyboard : false
			})

		}

	})
};

function addNewUserDetails() {

	var password = $('#addUserPassword').val();
	var name = $('#addUserName').val();
	var email = $('#addUserEmail').val();
	var phone = $('#addUserPhone').val();
	var dateOfBirth = $('#addUserDOB').val();
	var address = $('#addUserAddress').val();
	var status = $("#addUserSelect option:selected").val();

	var roleIDList = new Array();
	$("input:checkbox[name='addNewRoles']:checked").each(function() {
		roleIDList.push($(this).val());
	});

	$.ajax({

		url : "AddNewUserPanel",
		data : {
			'password' : password,
			'name' : name,
			'email' : email,
			'phone' : phone,
			'dateOfBirth' : dateOfBirth,
			'address' : address,
			'status' : status,
			'roles' : roleIDList,

		},
		success : function(data, textStatus, jqXHR) {

			if (data === "true")
				$("#body").load(location.href + "#body");

			else
				alert('dwrswraa');

		}

	})
};

// roles

function listRolesTools() {

	$.ajax({

		url : "RetrieveRolesToolPanel",
		success : function(data, textStatus, jqXHR) {

			var result = $(data).find('#contectChangeArea');
			$('#contectChangeArea').html(result);
			var url = "RetrieveRolesToolPanel";
			history.pushState({}, "", url);
			tableRunJquery();

		}

	})

};

function editRolesTool() {

	var id = "";
	$("input:checkbox[type=checkbox]:checked").each(function() {
		id = $(this).val();
	});

	console.log(id);

	$.ajax({

		url : "RetrieveRolesToolPanel",
		data : {
			'rid' : id
		},
		success : function(data, textStatus, jqXHR) {

			if (data === "error") {

			} else {
				var result = $(data).find('#roleEditModalDiv');
				$('#roleEditModalDiv').html(result);

				$('#roleEditModal').modal({
					backdrop : 'static',
					keyboard : false
				})
			}

		}

	});
};

function updateRolesTool() {

	var rid = $('#roleID').val();
	var name = $('#roleName').val();
	var description = $('#roleDescription').val();

	$.ajax({

		url : "updateRolesToolPanel",
		data : {
			'rid' : rid,
			'name' : name,
			'description' : description
		},
		success : function(data, textStatus, jqXHR) {

			if (data === "true")
				$("#body").load(location.href + "#body");

			else
				alert('dwrswraa');

		}

	})
};

function deleteRolesTool() {

	var ch_list = new Array();
	$("input:checkbox[type=checkbox]:checked").each(function() {
		ch_list.push($(this).val());
	});

	console.log(ch_list);

	$.ajax({

		url : "DeletePanelRolesTool",
		data : {
			'rids[]' : ch_list
		},
		success : function(data, textStatus, jqXHR) {

			if (data === "true")
				$("#body").load(location.href + "#body");

			else
				alert('dwrswraa');

		}

	})

};

function AddPanelRolesTool() {

	var name = $('#addRoleName').val();
	var description = $('#addRoleDescription').val();

	$.ajax({

		url : "AddPanelRolesTool",
		data : {
			'name' : name,
			'description' : description
		},
		success : function(data, textStatus, jqXHR) {

			if (data === "true")
				$("#body").load(location.href + "#body");

			else
				alert('dwrswraa');

		}

	})
};

function userProfile() {

	$.ajax({

		url : "userProfile",
		success : function(data, textStatus, jqXHR) {

			var result = $(data).find('#contectChangeArea');
			$('#contectChangeArea').html(result);
			var url = "userProfile";
			history.pushState({}, "", url);
			onstartUserProfile();
		}

	})

};

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

function passwordUpdatePanelCheck() {

	newPassword = $('#newPassword').val();
	currentPassword = $('#currentPassword').val();
	repeatPassword = $('#repeatPass').val();

	if (currentPassword != "") {

		$('#newPassword').removeAttr('disabled');

		if (newPassword != "") {

			if (newPassword.match(testPassword)) {

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

function updatePasswordPanel() {

	event.preventDefault();
	var form = event.target.form; // storing the form

	if (status == 1) {
		$.ajax({

			url : "UpdateUserPassword",
			data : {
				'newPassword' : newPassword,
				'currentPassword' : currentPassword
			},

			success : function(data) {

				if(data === "success"){
					toastr.success('Your password has been changed!');
					window.location.replace("login");
				}
				
				else if(data === "error"){
					toastr.error('An error was detected. Please try again latter!');
				}
				
				else{
					toastr.error('Incorrect current password. Your password has not been changed!');
				}

			}

		})
	}

	else
		toastr.error('An error was detected. Please try again latter!');
};

var status = 0;
var email_static = null;
var name_static = null;
var phone_static = null;
var dateOfBirth_static = null;
var address_static = null;
onstartUserProfile();

function onstartUserProfile() {
	address_static = $('#address').val();
	email_static = $('#email').val();
	name_static = $('#name').val();
	phone_static = $('#phone').val();
	dateOfBirth_static = $('#dateOfBirth').val();
	$("#updateProfile").hide();
};

function inputFormPanel() {

	console.log("inputed");

	var name = $('#name').val();
	var email = $('#email').val();
	var address = $('#address').val();
	var phone = $('#phone').val();
	var dateOfBirth = $('#dateOfBirth').val();
	var atposition = email.indexOf("@");
	var dotposition = email.lastIndexOf(".");

	if (email != email_static && email.length > 10) {

		$.ajax({

			url : "CheckUserEmail",
			data : {
				'email' : email

			},

			success : function(data) {

				if (data == "false") {
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

	} else if (name != name_static) {

		if (name.length < 10) {
			status = 0;
			alert('name is too short');
		}

		else {
			status = 1;
			$("#updateProfile").fadeIn();
		}
	}

	else if (address != address_static) {

		if (address.length > 12) {
			status = 1;
			$("#updateProfile").fadeIn();

		} else {
			status = 0;
			alert('address is too short');
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
			alert('please select a valide date of birth');
		}
	}

	else {
		status = 0;
		$("#updateProfile").fadeOut();
	}

};

function updateProfilePanel() {
	event.preventDefault();

	if (status == 1) {

		var name = $('#name').val();
		var email = $('#email').val();
		var address = $('#address').val();
		var phone = $('#phone').val();
		var dateOfBirth = $('#dateOfBirth').val();

		$.ajax({

			url : "UpdateUserProfile",
			data : {
				'name' : name,
				'email' : email,
				'address' : address,
				'phone' : phone,
				'dateOfBirth' : dateOfBirth,
			},
			success : function(data, textStatus, jqXHR) {

				if (data === "true")
					$("#body").load(location.href + "#body");

				else
					alert('dwrswraa');

			}

		})

	} else {
		alert('sorry, you dont have access');
	}

}