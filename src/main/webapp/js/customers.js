$(function() {
	$.getJSON("http://localhost:8080/api/customer/", function(result) {
		$.each(result, function(key, value) {
			fillTable(value);

		});
	});
	function fillTable(customer) {
		$("#customers").append(
			"<tr id='rw_" + customer.id + "'>" +
			"<td>" + customer.id + "</td>" +
			"<td>" + customer.firstName + "</td>" +
			"<td>" + customer.lastName + "</td>" +
			"<td><button " +
			" data-id=\"" + customer.id + "\"" +
			" data-first-name=\"" + customer.firstName + "\"" +
			" data-last-name=\"" + customer.lastName + "\"" +
			"href=\"#editCustomerModal\" id=\"edit\" data-toggle=\"modal\"><i class=\"material-icons\" data-toggle=\"tooltip\" data-bs-target=\"editCustomerModal\" title=\"Edit\">&#xE254;</i></button>" +
			"<a href=\"#deleteCustomerModal\" id=\"delete\" class=\"delete\" data-toggle=\"modal\"><i class=\"material-icons\" data-toggle=\"tooltip\" title=\"Delete\">&#xE872;</i></a></td>" + "\</tr>");
	}

	$(document).on("click", "#modal-add-customer", function addNewCustomer() {
		//Set blank labels 
		$("#first-name-label").val("");
		$("#last-name-label").val("");
	});

	function validateForm() {

		var firstName = document.getElementById("first-name-label");
		var firstNameValue = firstName.value;
		var specialCharsRegEx = /[`!@#$%^&*()_+\-=\[\]{};':"\\|,.<>\/?~]/;
		if (firstNameValue.match(/^\s*$/) || specialCharsRegEx.test(firstNameValue) || !(/^[a-zA-Z\s]*$/.test(lastNameValue))) {
			firstName.style.border = "red solid 3px";
			alert("First Name must be filled with letters and must not contains special characters");
			return false;
		}
		var lastName = document.getElementById("last-name-label");
		var lastNameValue = lastName.value;
		if (lastNameValue.match(/^\s*$/) || !(/^[a-zA-Z\s]*$/.test(lastNameValue)) || specialCharsRegEx.test(firstNameValue)) {
			lastName.style.border = "red solid 3px";
			alert("Last Name must be filled with letters and must not contains special characters");
			return false;
		}
	}

	$("#modal-add-customer").click(function() {
		var isValid = validateForm();
		if (isValid === false) {
			return false;
		}
		//get values from labels
		var jsonCustomer = {
			firstName: $("#first-name-label").val(),
			lastName: $("#last-name-label").val(),
		};
		//send data to backend through JSON
		$.ajax({
			type: "POST",
			url: "http://localhost:8080/api/customer",
			data: JSON.stringify(jsonCustomer),
			contentType: "application/json; charset=utf-8",
			success: function(data) {
				"<tr id='rw_" + data.id + "'>" +
					"<tr id='rw_" + data.firstName + "'>" +
					"<th>" + data.lastName + "</th>";
				alert("New Customer successfully added");
			}
		});
	});

	$(document).on("click", "#edit", function() {
		var id = document.getElementById("id-customer-edit");
		var customerId = $(this).data('id');
		$("#edit-modal #id-customer-edit").val(customerId);
		var customerFirstName = $(this).data('firstName');
		$("#edit-modal #first-name-edit").val(customerFirstName);
		var customerLastName = $(this).data('lastName');
		$("#edit-modal #last-name-edit").val(customerLastName);

	});

	$("#modal-edit-customer").click(function() {
		var jsonCustomerEdit = {
			id: $("#id-customer-edit").val(),
			firstName: $("#first-name-edit").val(),
			lastName: $("#last-name-edit").val(),

		};

		$.ajax({
			type: "PUT",
			url: "http://localhost:8080/api/customer/" + $("#id-customer-edit").val(),
			data: JSON.stringify(jsonCustomerEdit),
			contentType: "application/json",
			success: function(data) {
				$('#close').click();
				alert("Customer is edited!");
				var row = "<tr id='rw_" + data.id + "'>" +
					"<td>" + data.firstName + "</td>" +
					"<td>" + data.lastName + "</td>";
			},
			error: function(err) {
				alert("Error: Customer is not edited!  (" + err.responseJSON.error + ")");
			}
		});
	});
	$('table').on('click', '#delete', function() {
		var id = $(this).closest('tr').children('td:first').text();
		var thisReference = $(this);
		$.ajax({
			type: "DELETE",
			url: "http://localhost:8080/api/customer/" + id,
			success: function() {
				thisReference.closest('tr').remove();
				alert("The customer is deleted!");
			},
			error: function(err) {
				alert("Error: Customer is not deleted!  (" + err.responseJSON.error + ")");
			}
		});
	});
	$("#upload-file").click(function() {
		var uploadFile = document.getElementById("file-upload");
		if (uploadFile.value != null) {
			var uploadData = new FormData();
			var csvFile = $("#file-upload").get(0).files;

			if (csvFile.length > 0) {
				uploadData.append("customersCSV", csvFile[0]);
				$.ajax({
					url: "http://localhost:8080/api/customer/upload",
					contentType: false,
					processData: false,
					data: uploadData,
					type: 'POST',
					success: function(result) {
						alert("Customers Sucessfully uploaded");
						$.each(result, function(key, value) {
							fillTable(value);
						});
					},
					error: function() {
						alert("Error: Customers are not uploaded!");
					}

				});
			}
		}
	});
});
