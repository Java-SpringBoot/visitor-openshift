// The root URL for the RESTful services
var rootURL = "././rest/visitor";
var courseURL = "././rest/course";

var currentVisitor;
//$('#course').hide();
//$('#staff').hide();


// Retrieve visitor list when application starts
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
    newVisitor();
	return false;
});

$('#btnSave').click(function() {
    if ($('#visitorId').val() == '')
        addVisitor();
	else
        updateVisitor();
	return false;
});

$('#btnDelete').click(function() {
    deleteVisitor();
	return false;
});

$('#visitorList').on('click', 'a', function() {
	findById($(this).data('identity'));
});
/*$('#courseBtn1').click(function() {
    $('#course').show();
    $('#home').hide();
});
$('#test1').on('click', '#courseBtn', function() {
    $('#course').show();
    $('#home').hide(;
    $('#staff').hide();
});
$('#test2').on('click', '#staffBtn', function() {
    $('#course').hide();
    $('#home').hide();
    $('#staff').show();
});
*/
// Replace broken images with generic visitor bottle
$("img").error(function(){
  $(this).attr("src", "pics/generic.jpg");

});

function search(searchKey) {
	if (searchKey == '') 
		findAll();
	else
		findByName(searchKey);
}

function newVisitor() {
	$('#btnDelete').hide();
    currentVisitor = {};
    renderDetails(currentVisitor); // Display empty form
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
}//visitrCourseDropDown



function findById(id) {
	console.log('findById: ' + id);
	$.ajax({
		type: 'GET',
		url: rootURL + '/' + id,
		dataType: "json",
		success: function(data){
			$('#btnDelete').show();
			console.log('findById success: ' + data.name);
            currentVisitor = data;
            renderDetails(currentVisitor);
		}
	});
}

function addVisitor() {
    console.log('addVisitor');
	$.ajax({
		type: 'POST',
		contentType: 'application/json',
		url: rootURL,
		dataType: "json",
		data: formToJSON(),
		success: function(data, textStatus, jqXHR){
            alert('Visitor created successfully');
			$('#btnDelete').show();
            $('#visitorId').val(data.id);
		},
		error: function(jqXHR, textStatus, errorThrown){
            alert('addVisitor error: ' + textStatus);
		}
	});
}

function updateVisitor() {
    console.log('updateVisitor');
	$.ajax({
		type: 'PUT',
		contentType: 'application/json',
        url: rootURL + '/' + $('#visitorId').val(),
		dataType: "json",
		data: formToJSON(),
		success: function(data, textStatus, jqXHR){
            alert('Visitor updated successfully');
		},
		error: function(jqXHR, textStatus, errorThrown){
            alert('updateVisitor error: ' + textStatus);
		}
	});
}

function deleteVisitor() {
    console.log('deleteVisitor');
	$.ajax({
		type: 'DELETE',
        url: rootURL + '/' + $('#visitorId').val(),
		success: function(data, textStatus, jqXHR){
            alert('Visitor deleted successfully');
		},
		error: function(jqXHR, textStatus, errorThrown){
            alert('deleteVisitor error');
		}
	});
}

function renderList(data) {
	// JAX-RS serializes an empty list as null, and a 'collection of one' as an object (not an 'array of one')
    var list = data == null ? [] : (data instanceof Array ? data : [data]);
    $('#visitorList a').remove();
    $.each(list, function(index, visitor) {
        $('#visitorList').append('<a href="#" class="list-group-item" data-identity="' + visitor.vid + '">'
                + visitor.vfname + ' ' + visitor.vlname + '</a>');
	});
}

function fillVisitrCourseDropDown() {
    //console.log('findById: ');
    $.ajax({
        type: 'GET',
        url: courseURL,
        dataType: "json",
        success: function(data) {
            var list = data == null ? [] : (data instanceof Array ? data : [data]);
            //$('#vcid option').remove();
            $.each(list, function(index, course) {
                $('#vcid1').append('<option id="id' + course.cid + '">' + course.cname + '</option>');
                console.log(course.cid);
                //<li role="presentation"><a role="menuitem" tabindex="-1" href="#">Action</a></li>
            }
            );
        }}
    );
}
fillVisitrCourseDropDown();

function renderDetails(visitor) {
    var courseid = visitor.vcid;
    $('#visitorId').val(visitor.vid);
    $('#id' + visitor.vcid).prop('selected', 'selected');
    console.log(courseid);
    // document.getElementById(courseid).setAttribute("selected", "selected");
    console.log($('#id' + visitor.vcid).text());
    $('#vfname').val(visitor.vfname);
    $('#vlname').val(visitor.vlname);
    $('#vmobile').val(visitor.vmobile);
    $('#vtelephone').val(visitor.vtelephone);
    $('#vemail').val(visitor.vemail);
    $('#vnotes').val(visitor.vnotes);
}

// Helper function to serialize all the form fields into a JSON string
function formToJSON() {
    var visitorId = $('#vid').val();
	return JSON.stringify({
        "vid": visitorId == "" ? null : visitorId,
        "vcid": $('#vcid').val(),
        "vfname": $('#vfname').val(),
        "vlname": $('#vlname').val(),
        "vmobile": $('#vmobile').val(),
        "vtelephone": $('#vtelephone').val(),
        //"picture": currentVisitor.picture,
        "vemail": $('#vemail').val(),
        "vnotes": $('#vnotes').val()
		});
}
