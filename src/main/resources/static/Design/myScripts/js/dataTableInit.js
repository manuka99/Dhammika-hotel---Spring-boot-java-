runScripts();

function generateRandomString(length) {
	var result = '';
	var characters = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789';
	var charactersLength = characters.length;
	for (var i = 0; i < length; i++) {
		result += characters.charAt(Math
				.floor(Math.random() * charactersLength));
	}
	return result;
};

function runScripts() {

	$('.file-upload').file_upload();

	$('#paginationFirstLast').DataTable({
		"pagingType" : "first_last_numbers"
	});

	$('#complainsUsertable').DataTable({
		"pagingType" : "first_last_numbers"
	});

	tableRunJquery();

	$('#message').mdbWYSIWYG();
	$('#messageCustomPalette').mdbWYSIWYG({
		colorPalette : {
			red : '#d50000',
			green : '#64dd17',
			yellow : '#fff176',
			blue : '#03a9f4',
			purple : '#6a1b9a',
			white : '#fff',
			black : '#000'
		}
	});

	$('#dtBasicExample').mdbEditor({
		mdbEditor : true
	});
	$('.dataTables_length').addClass('bs-select');

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



