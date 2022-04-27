$( document ).ready(function() {

  var currentSelectedAccountType = document.getElementById("accountType").value;
  $.get( "http://localhost:8887/accounttypes", function( data ) {
    var lines = "";
    for (item of data) {
      var code = item.code;
      var value = item.value;
      var newLine = "<option value=\"" + code + "\">" + value + "</option>";
      lines += newLine;
    }
    console.log(lines);
    $("#accountType").html(lines);
  });

$("#accountType").change(function() {
  var currentSelectedAccountType = document.getElementById("accountType").value;

  $.get( "http://localhost:8887/accounttype/" + currentSelectedAccountType + "/additionaltypes", function( data ) {
    var lines = "";
    for (item of data) {
      var code = item.code;
      var value = item.value;
      var newLine = "<option value=\"" + code + "\">" + value + "</option>";
      lines += newLine;
    }
    console.log(lines);
    $("#accountAdditionalType").html(lines);
  });

});


});
