$(function() {
	$.getJSON("http://localhost:8080/api/book/", function(result) {
		$.each(result, function(key, value) {
			fillTable(value);
		});
	});
	function fillTable(book) {
		$("#books").append(
			"<tr id='rw_" + book.id + "'>" +
			"<th>" + book.id + "</th>" +
			"<th>" + book.isbn + "</th>" +
			"<th>" + book.name + "</th>" +
			"<th>" + book.author + "</th>" +
			"<th>" + book.numPages + "</th>" +
			"<th>" + book.genre + "</th>" +
			"<td><button id=\"deleteBook\" style=\"font-size:16px\" class=\"btn btn-lg btn-danger mr-2\"><i class=\"fa fa-trash\"></i> Delete</button>" +
			"<button" +
			" data-id=\"" + book.id + "\"" +
			" data-name=\"" + book.name + "\"" +
			" data-isbn=\"" + book.isbn + "\"" +
			" data-author=\"" + book.author + "\"" +
			" data-numpages=\"" + book.numPages + "\"" +
			" data-genre=\"" + book.genre + "\"" +
			"type=\"button\" style=\"font-size:16px\" class=\"open-AddBookDialog btn btn-warning\" data-bs-toggle=\"modal\" data-bs-target=\"#add-new-book-modal\"><i class=\"fa fa-edit\"></i> Edit</button></td>"
			+ "\</tr>");
	}
	$(document).on("click", "#modal-add-button", function addNewBook() {
		//Set blank labels 
		$("#name-label").val("");
		$("#isbn-label").val("");
		$("#author-label").val("");
		$("#numpages-label").val("");
		$("#genre-label").val("");

	});
	//Validation	
	function validateForm() {
		var isbnInput = document.getElementById("isbn-label");
		var isbnInputValue = isbnInput.value;
		var isbnRegEx = /^(?=(?:\D*\d){10}(?:(?:\D*\d){3})?$)[\d-]+$/;
		if (!isbnRegEx.test(isbnInputValue)) {
			isbnInput.style.border = "red solid 3px";
			alert("ISBN must contains 10 or 13 digits")
			return false;
		}
		var nameInput = document.getElementById("name-label");
		var nameInputValue = nameInput.value;
		var specialCharsRegEx = /[`!@#$%^&*()_+\-=\[\]{};':"\\|,.<>\/?~]/;
		if (nameInputValue.match(/^\s*$/) || specialCharsRegEx.test(nameInputValue)) {
			nameInput.style.border = "red solid 3px";
			alert("Name must be filled and must not contains special characters")
			return false;
		}
		var authorInput = document.getElementById("author-label");
		var authorInputValue = authorInput.value;
		if (authorInputValue.match(/^\s*$/) || !(/^[a-zA-Z\s]*$/.test(authorInputValue))) {
			authorInput.style.border = "red solid 3px";
			alert("Author must be filled and must contains only letters")
			return false;
		}
		var numPagesInput = document.getElementById("numpages-label");
		var numPagesInputValue = numPagesInput.value;
		if (numPagesInputValue <= 0) {
			numPagesInput.style.border = "red solid 3px";
			alert("Page number must be filled and > 0")
			return false;
		}
	}
	$("#modal-add-button").click(function() {
		var isValid = validateForm();
		alert("Book is SUCCESSFULLY added!")
		if (isValid === false) {
			return false;
		}
		//get values from labels
		var jsonBook = {
			isbn: $("#isbn-label").val(),
			name: $("#name-label").val(),
			author: $("#author-label").val(),
			numPages: $("#numpages-label").val(),
			genre: $("#drop-down-menu").val()
		};
		//send data to backend through JSON
		$.ajax({
			type: "POST",
			url: "http://localhost:8080/api/book",
			data: JSON.stringify(jsonBook),
			contentType: "application/json; charset=utf-8",
			success: function(data) {

				"<tr id='rw_" + data.id + "'>" +
					"<th>" + data.id + "</th>" +
					"<th>" + data.isbn + "</th>" +
					"<th>" + data.name + "</th>" +
					"<th>" + data.author + "</th>" +
					"<th>" + data.numPages + "</th>" +
					"<th>" + data.genre + "</th>"
			}
		});
	});
	$('table').on('click', 'button[id="deleteBook"]', function() {
		var id = $(this).closest('tr').children('th:first').text();
		var thisReference = $(this);
		$.ajax({
			type: "DELETE",
			url: "http://localhost:8080/api/book/" + id,
			success: function() {
				thisReference.closest('tr').remove();
				alert("The book is deleted!")
			},
			error: function(err) {
				alert("Error: Book is not deleted!  (" + err.responseJSON.error + ")");
			}
		});

	});

	$(document).on("click", ".open-AddBookDialog", function() {
		$("#editBook").show();
		$("#addBook").hide();

		var bookId = $(this).data('id');
		$(".modal-body #id-label").val(bookId);

		var bookName = $(this).data('name');
		$(".modal-body #name-label").val(bookName);

		var bookIsbn = $(this).data('isbn');
		$(".modal-body #isbn-label").val(bookIsbn);

		var bookAuthor = $(this).data('author');
		$(".modal-body #author-label").val(bookAuthor);

		var bookNumPages = $(this).data('numpages');
		$(".modal-body #numpages-label").val(bookNumPages);

		var bookGenre = $(this).data('genre');
		$(".modal-body #genre-label").val(bookGenre);

	});
	$("#editBook").click(function() {
		var jsonBookEdit = {
			id: $("#id-label").val(""),
			isbn: $("#isbn-label").val(""),
			name: $("#name-label").val(""),
			author: $("#author-label").val(""),
			numPages: $("#numpages-label").val(""),
			genre: $("#genre-label").val("")
		};
		$.ajax({
			type: "PUT",
			data: JSON.stringify(jsonBookEdit),
			contentType: "application/json",
			url: "http://localhost:8080/api/book/" + $("#id-label").val(),
			success: function(data) {
				$('#close').click();
				alert("The book is edited!");
				var row = "<td>" + data.id + "</td>" +
					"<td>" + data.isbn + "</td>" +
					"<td>" + data.name + "</td>" +
					"<td>" + data.author + "</td>" +
					"<td>" + data.numPages + "</td>" +
					"<td>" + data.genre + "</td>"
			},
			error: function(err) {
				alert("Error: Book is not edited!  (" + err.responseJSON.error + ")");
			}
		});
	});
	$("#upload-file").click(function() {
		var uploadFile = document.getElementById("file-upload");
		if (uploadFile.value != null) {
			var uploadData = new FormData();
			var csvFile = $("#file-upload").get(0).files;

			if (csvFile.length > 0) {
				uploadData.append("booksCSV", csvFile[0]);
				$.ajax({
					url: "http://localhost:8080/api/book/upload",
					contentType: false,
					processData: false,
					data: uploadData,
					type: 'POST',
					success: function(result) {
						alert("Books Sucessfully uploaded");
						$.each(result, function(key, value) {
							fillTable(value);
						});
					},
					error: function() {
						alert("Error: Books are not uploaded!");
					}

				});
			}
		}
	});
});
