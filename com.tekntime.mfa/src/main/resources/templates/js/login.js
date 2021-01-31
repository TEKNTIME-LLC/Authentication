	function validate() {
        if (document.f.username.value == "" && document.f.password.value == "") {
            alert(/*[[#{message.username} + #{message.password}]]*/);
            document.f.username.focus();
            return false;
        }
        if (document.f.username.value == "") {
            alert(/*[[#{message.username}]]*/);
            document.f.username.focus();
            return false;
        }
        if (document.f.password.value == "") {
            alert(/*[[#{message.password}]]*/);
            document.f.password.focus();
            return false;
        }
    }
    