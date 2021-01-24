var serverContext = [[@{/}]];

$(document).ready(function () {
	$('form').submit(function(event) {
		register(event);
	});
	
	$(":password").keyup(function(){
		if($("#password").val() != $("#matchPassword").val()){
	        $("#globalError").show().html(/*[[#{PasswordMatches.user}]]*/);
	    }else{
	    	$("#globalError").html("").hide();
	    }
	});
	
	options = {
		    common: {minChar:8},
		    ui: {
		    	showVerdictsInsideProgressBar:true,
		    	showErrors:true,
		    	errorMessages:{
		    		  wordLength: /*[[#{error.wordLength}]]*/,
		    		  wordNotEmail: /*[[#{error.wordNotEmail}]]*/,
		    		  wordSequences: /*[[#{error.wordSequences}]]*/,
		    		  wordLowercase: /*[[#{error.wordLowercase}]]*/,
		    		  wordUppercase: /*[[#{error.wordUppercase}]]*/,
		    	          wordOneNumber: /*[[#{error.wordOneNumber}]]*/,
		    		  wordOneSpecialChar: /*[[#{error.wordOneSpecialChar}]]*/
		    		}
		    	}
		};
	 $('#password').pwstrength(options);
});

function register(event){
	event.preventDefault();
    $(".alert").html("").hide();
    $(".error-list").html("");
    if($("#password").val() != $("#matchPassword").val()){
    	$("#globalError").show().html(/*[[#{PasswordMatches.user}]]*/);
    	return;
    }
    var formData= $('form').serialize();
    $.post(serverContext + "user/registration",formData ,function(data){
        if(data.message == "success"){
            window.location.href = serverContext + "successRegister.html";
        }
        
    })
    .fail(function(data) {
        if(data.responseJSON.error.indexOf("MailError") > -1)
        {
            window.location.href = serverContext + "emailError.html";
        }
        else if(data.responseJSON.error == "UserAlreadyExist"){
            $("#emailError").show().html(data.responseJSON.message);
        }
        else if(data.responseJSON.error.indexOf("InternalError") > -1){
            window.location.href = serverContext + "login?message=" + data.responseJSON.message;
        }
        else{
        	var errors = $.parseJSON(data.responseJSON.message);
            $.each( errors, function( index,item ){
            	if (item.field){
            		$("#"+item.field+"Error").show().append(item.defaultMessage+"<br/>");
            	}
            	else {
            		$("#globalError").show().append(item.defaultMessage+"<br/>");
            	}
               
            });
        }
    });
}
