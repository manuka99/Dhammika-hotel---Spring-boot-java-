var productIDOnload = "";
var productStatus = "";

function deleteProduct() {
	var ch_list = new Array();
	$("input:checkbox[type=checkbox]:checked").each(function() {
		ch_list.push($(this).val());
	});

	console.log(ch_list);

	$.ajax({

		url : "DeletePanelFood",
		data : {
			'fids[]' : ch_list
		},
		success : function(data, textStatus, jqXHR) {

			if (data === "success")
				$("#body").load(location.href + "#body");

			else
				alert('dwrswraa');

		}

	})

};

function editProduct() {

	var id = "";
	$("input:checkbox[type=checkbox]:checked").each(function() {
		id = $(this).val();
	});

	console.log(id);

	$.ajax({

		url : "RetrievePanelFood",
		data : {
			'fid' : id
		},
		success : function(data, textStatus, jqXHR) {

			if (data == "error") {

			} else {
				var result = $(data).find('#productEditModalDiv');
				$('#productEditModalDiv').html(result);
				productIDOnload = $('#productID').val();
				productStatus = $('#productStatus').val();
				CatergoryID = $('#CatergoryID').val();
				$('.file-upload').file_upload();
				console.log(productStatus);
				$('#productOption option[value=' + productStatus + ']').attr(
						'selected', 'selected');

				$('#productCategory option[value=' + CatergoryID + ']').attr(
						'selected', 'selected');

				console.log(productIDOnload);
				$('#productEditModal').modal({
					backdrop : 'static',
					keyboard : false
				})
			}

		}

	});
};

function updateProductDetails() {

	var name = $('#productName').val();
	var description = $('#productDescription').val();
	var portion = $('#productPortion').val();
	var price = $('#productPrice').val();
	var tax = $('#productTax').val();
	var CatergoryID = $("#productCategorySelect option:selected").val();
	var selectedOption = $("#productStatusSelect option:selected").val();

	console.log(selectedOption);

	var fd = new FormData();
	fd.append('name', name);
	fd.append('description', description);
	fd.append('portion', portion);
	fd.append('price', price);
	fd.append('tax', tax);
	fd.append('active', selectedOption);
	fd.append('itemID', productIDOnload);
	fd.append('catID', CatergoryID);

	if ($('#productImage').get(0).files.length != 0) {

		var fileDatae = document.querySelector('#productImage');
		fd.append('fileImage', fileDatae.files[0])

	}

	var request = new XMLHttpRequest();

	request.onreadystatechange = function() {
		if (this.readyState == 4 && this.status == 200
				&& this.responseText == "success") {
			// document.getElementById("demo").innerHTML = this.responseText;
			$("#body").load(location.href + "#body");
		}
	};

	request.upload.addEventListener('progress', function(e) {

		document.querySelector('#progress').innerHTML = Math.round(e.loaded
				/ e.total * 100)
				+ '%';

	}, false);

	request.open('post', 'UpdatePanelFood', true);
	request.send(fd);

};

function addnewModelOpen() {

	$.ajax({

		url : "RetrievePanelFood",
		success : function(data, textStatus, jqXHR) {

			if (data == "error") {

			} else {
				var result = $(data).find('#productAddModalDiv');
				$('#productAddModalDiv').html(result);

				$('.file-upload').file_upload();

				$('#productAddModal').modal({
					backdrop : 'static',
					keyboard : false
				});
			}

		}

	});

};

function addNewProductDetails() {

	var name = $('#AddNewproductName').val();
	var description = $('#AddNewproductDescription').val();
	var portion = $('#AddNewproductPortion').val();
	var price = $('#AddNewproductPrice').val();
	var tax = $('#AddNewproductTax').val();
	var CatergoryID = $("#AddNewProductCategorySelect option:selected").val();
	var selectedOption = $("#addNewProductStatusSelect option:selected").val();

	console.log(name);
	console.log(description);
	console.log(portion);
	console.log(price);
	console.log(tax);
	console.log(CatergoryID);
	console.log(selectedOption);

	var fd = new FormData();
	fd.append('name', name);
	fd.append('description', description);
	fd.append('portion', portion);
	fd.append('price', price);
	fd.append('tax', tax);
	fd.append('active', selectedOption);
	fd.append('catID', CatergoryID);

	var fileDatae = document.querySelector('#addNewproductImage');
	fd.append('fileImage', fileDatae.files[0])

	var request = new XMLHttpRequest();

	request.onreadystatechange = function() {
		if (this.readyState == 4 && this.status == 200
				&& this.responseText == "success") {
			// document.getElementById("demo").innerHTML = this.responseText;
			$("#body").load(location.href + "#body");
		}
	};

	request.upload.addEventListener('progress', function(e) {

		document.querySelector('#progress').innerHTML = Math.round(e.loaded
				/ e.total * 100)
				+ '%';

	}, false);

	request.open('post', 'AddNewPanelFoodItem', true);
	request.send(fd);

};


function runtests(){
	
	alert('');
	
};

function listFoodByCategory(categoryID) {

	$.ajax({

		url : "/panel/product",
		data : {
			'categoryID' : categoryID

		},
		success : function(data, textStatus, jqXHR) {

			var result = $(data).find('#contectChangeArea');
			$('#contectChangeArea').html(result);

			if (categoryID != null)
				var url = "/panel/product + categoryID;

			else
				var url = "RetrievePanelFood";

			history.pushState({}, "", url);
			tableRunJquery();

		}

	})

};

function listCategoryTools() {

	$.ajax({

		url : "RetrieveCategoryToolsPanel",
		success : function(data, textStatus, jqXHR) {

			var result = $(data).find('#contectChangeArea');
			$('#contectChangeArea').html(result);
			var url = "RetrieveCategoryToolsPanel";
			history.pushState({}, "", url);
			tableRunJquery();

		}

	})

};

function editCategoryTool() {

	var id = "";
	$("input:checkbox[type=checkbox]:checked").each(function() {
		id = $(this).val();
	});

	console.log(id);

	$.ajax({

		url : "RetrieveCategoryToolsPanel",
		data : {
			'cid' : id
		},
		success : function(data, textStatus, jqXHR) {

			if (data === "error") {

			} else {
				var result = $(data).find('#categoryEditModalDiv');
				$('#categoryEditModalDiv').html(result);

				$('#categoryEditModal').modal({
					backdrop : 'static',
					keyboard : false
				})
			}

		}

	});
};

function updateCategoryTool() {

	var cid = $('#CatergoryID').val();
	var name = $('#categoryName').val();
	var description = $('#categoryDescription').val();

	$.ajax({

		url : "updateCategoryToolPanel",
		data : {
			'cid' : cid,
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

function deleteCategoryTool() {

	var ch_list = new Array();
	$("input:checkbox[type=checkbox]:checked").each(function() {
		ch_list.push($(this).val());
	});

	console.log(ch_list);

	$.ajax({

		url : "DeletePanelCategoryTool",
		data : {
			'cids[]' : ch_list
		},
		success : function(data, textStatus, jqXHR) {

			if (data === "true")
				$("#body").load(location.href + "#body");

			else
				alert('dwrswraa');

		}

	})

};

function AddPanelCategoryTool() {

	var name = $('#addCategoryName').val();
	var description = $('#addCategoryDescription').val();

	$.ajax({

		url : "AddPanelCategoryTool",
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

function tableRunJquery() {

	$(document)
			.ready(
					function() {

						$('#dtMaterialDesignExample').DataTable();
						$('#dtMaterialDesignExample_wrapper').find('label')
								.each(
										function() {
											$(this).parent().append(
													$(this).children());
										});
						$('#dtMaterialDesignExample_wrapper .dataTables_filter')
								.find('input').each(function() {
									const $this = $(this);
									$this.attr("placeholder", "Search");
									$this.removeClass('form-control-sm');
								});
						$('#dtMaterialDesignExample_wrapper .dataTables_length')
								.addClass('d-flex flex-row');
						$('#dtMaterialDesignExample_wrapper .dataTables_filter')
								.addClass('md-form');
						$('#dtMaterialDesignExample_wrapper select')
								.removeClass(
										'custom-select custom-select-sm form-control form-control-sm');
						$('#dtMaterialDesignExample_wrapper select').addClass(
								'mdb-select');
						$('#dtMaterialDesignExample_wrapper .mdb-select')
								.materialSelect();
						$('#dtMaterialDesignExample_wrapper .dataTables_filter')
								.find('label').remove();
					});
};

