// The root URL for the RESTful services
var rootURL = "././rest/staff";

var currentStaff;

// Retrieve staff list when application starts
findAll();

// Nothing to delete in initial application state
$('#btnDelete').hide();

// Register listeners
$('#btnSearch').click(function() {
    search($('#searchKey').val());
	return false;
});

// Trigger search when pressing 'Return' on search key input field
$('#searchKey').keypress(function(e){
    if (e.which === 13) {
		search($('#searchKey').val());
		e.preventDefault();
		return false;
    }
});

$('#btnAdd').click(function() {
    newStaff();
	return false;
});

$('#btnSave').click(function() {
    if ($('#staffId').val() == '')
        addStaff();
	else
        updateStaff();
	return false;
});

$('#btnDelete').click(function() {
    deleteStaff();
	return false;
});

$('#staffList a').live('click', function() {
	findById($(this).data('identity'));
});

// Replace broken images with generic staff bottle
$("img").error(function(){
  $(this).attr("src", "pics/generic.jpg");

});

function search(searchKey) {
	if (searchKey == '') 
		findAll();
	else
		findByName(searchKey);
}

function newStaff() {
	$('#btnDelete').hide();
    currentStaff = {};
    renderDetails(currentStaff); // Display empty form
}

function findAll() {
	console.log('findAll');
	$.ajax({
		type: 'GET',
		url: rootURL,
		dataType: "json", // data type of response
		success: renderList
	});
}

function findByName(searchKey) {
    console.log('findByName: ' + searchKey);

	$.ajax({
		type: 'GET',
		url: rootURL + '/search/' + searchKey,
		dataType: "json",
		success: renderList 
	});
}

function findById(id) {
	console.log('findById: ' + id);
	$.ajax({
		type: 'GET',
		url: rootURL + '/' + id,
		dataType: "json",
		success: function(data){
			$('#btnDelete').show();
            console.log('findById success: ' + data.sname);
            currentStaff = data;
            renderDetails(currentStaff);
		}
	});
}

function addStaff() {
    console.log('addStaff');
	$.ajax({
		type: 'POST',
		contentType: 'application/json',
		url: rootURL,
		dataType: "json",
		data: formToJSON(),
		success: function(data, textStatus, jqXHR){
            alert('Staff created successfully');
			$('#btnDelete').show();
            $('#staffId').val(data.id);
		},
		error: function(jqXHR, textStatus, errorThrown){
            alert('addStaff error: ' + textStatus);
		}
	});
}

function updateStaff() {
    console.log('updateStaff');
	$.ajax({
		type: 'PUT',
		contentType: 'application/json',
        url: rootURL + '/' + $('#staffId').val(),
		dataType: "json",
		data: formToJSON(),
		success: function(data, textStatus, jqXHR){
            alert('Staff updated successfully');
		},
		error: function(jqXHR, textStatus, errorThrown){
            alert('updateStaff error: ' + textStatus);
		}
	});
}

function deleteStaff() {
    console.log('deleteStaff');
	$.ajax({
		type: 'DELETE',
        url: rootURL + '/' + $('#staffId').val(),
		success: function(data, textStatus, jqXHR){
            alert('Staff deleted successfully');
		},
		error: function(jqXHR, textStatus, errorThrown){
            alert('deleteStaff error');
		}
	});
}

function renderList(data) {
	// JAX-RS serializes an empty list as null, and a 'collection of one' as an object (not an 'array of one')
    var list = data == null ? [] : (data instanceof Array ? data : [data]);
    $('#staffList a').remove();
    $.each(list, function(index, staff) {
        $('#staffList').append('<a href="#" class="list-group-item" data-identity="' + staff.sid + '">' + staff.sname + '</a>');
	});
}

function renderDetails(staff) {
    $('#staffId').val(staff.sid);
    $('#sname').val(staff.sname);
    $('#spassword').val(staff.spassword);
    }

// Helper function to serialize all the form fields into a JSON string
function formToJSON() {
    var staffId = $('#vid').val();
	return JSON.stringify({
        "id": staffId == "" ? null : staffId,
        "sname": $('#sname').val(),
        "spassword": $('#spassword').val()
		});
}
