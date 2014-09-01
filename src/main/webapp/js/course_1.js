// The root URL for the RESTful services
var rootURL = "././rest/course";

var currentCourse;

// Retrieve course list when application starts
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
    newCourse();
	return false;
});

$('#btnSave').click(function() {
    if ($('#courseId').val() == '')
        addCourse();
	else
        updateCourse();
	return false;
});

$('#btnDelete').click(function() {
    deleteCourse();
	return false;
});

$('#courseList').on('click', 'a', function() {
	findById($(this).data('identity'));
});

// Replace broken images with generic course bottle
$("img").error(function(){
  $(this).attr("src", "pics/generic.jpg");

});

function search(searchKey) {
	if (searchKey == '') 
		findAll();
	else
		findByName(searchKey);
}

function newCourse() {
	$('#btnDelete').hide();
    currentCourse = {};
    renderDetails(currentCourse); // Display empty form
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
			console.log('findById success: ' + data.name);
            currentCourse = data;
            renderDetails(currentCourse);
		}
	});
}

function addCourse() {
    console.log('addCourse');
	$.ajax({
		type: 'POST',
		contentType: 'application/json',
		url: rootURL,
		dataType: "json",
		data: formToJSON(),
		success: function(data, textStatus, jqXHR){
            alert('Course created successfully');
			$('#btnDelete').show();
            $('#courseId').val(data.id);
		},
		error: function(jqXHR, textStatus, errorThrown){
            alert('addCourse error: ' + textStatus);
		}
	});
}

function updateCourse() {
    console.log('updateCourse');
	$.ajax({
		type: 'PUT',
		contentType: 'application/json',
        url: rootURL + '/' + $('#courseId').val(),
		dataType: "json",
		data: formToJSON(),
		success: function(data, textStatus, jqXHR){
            alert('Course updated successfully');
		},
		error: function(jqXHR, textStatus, errorThrown){
            alert('updateCourse error: ' + textStatus);
		}
	});
}

function deleteCourse() {
    console.log('deleteCourse');
	$.ajax({
		type: 'DELETE',
        url: rootURL + '/' + $('#courseId').val(),
		success: function(data, textStatus, jqXHR){
            alert('Course deleted successfully');
		},
		error: function(jqXHR, textStatus, errorThrown){
            alert('deleteCours error');
		}
	});
}

function renderList(data) {
	// JAX-RS serializes an empty list as null, and a 'collection of one' as an object (not an 'array of one')
    var list = data == null ? [] : (data instanceof Array ? data : [data]);
    $('#courseList a').remove();
    $.each(list, function(index, course) {
        $('#courseList').append('<a href="#" class="list-group-item" data-identity="' + course.cid + '">' + course.cname + '</a>');
	});
}

function renderDetails(course) {
    $('#courseId').val(course.cid);
    $('#cname').val(course.cname);
    $('#cnotes').val(course.cnotes);
    
}

// Helper function to serialize all the form fields into a JSON string
function formToJSON() {
    var courseId = $('#cid').val();
	return JSON.stringify({
        "cid": courseId == "" ? null : courseId,
        "cname": $('#cname').val(),
        "cnotes": $('#cnotes').val()
        
		});
}
