<%-- 
    Document   : loginPage
    Created on : 16 Dec, 2015, 12:40:03 PM
    Author     : Administrator
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<!--<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <script type="text/javascript">
$(document).ready(function() {
  $.ajaxSetup({ cache: true });
  $.getScript('//connect.facebook.net/en_US/sdk.js', function(){
    FB.init({
      appId: '{your-app-id}',
      version: 'v2.5' // or v2.0, v2.1, v2.2, v2.3
    });     
    alert(FB.toString())
    $('#loginbutton,#feedbutton').removeAttr('disabled');
    FB.getLoginStatus("updateStatusCallback");
  });
});
//        $(function () {
//            $("#dialog").dialog({
//                autoOpen: false,
//                height: 350,
//                width: 756,
//                modal: true,
//                buttons: {
//                    "Save": function () {
//                        ajaxCall();
//                    },
//                    Cancel: function () {
//                        $(this).dialog("close");
//                    },
//                    "Reset": function () {
//                        $('#rmks').val('');
//                        $('#check').attr('checked', false);
//                    }
//                },
//                close: function () {
//                    $('#curRowId').val('');
//                    $('#rmks').val('');
//                    $('#check').attr('checked', false);
//                }
//            });
//        });
        function open(){
            alert("OK")
            $("#dialog").dialog("open");
        }
    </script>
    <body>
        <div id="dialog" ></div>
        <h1 onclick="open()">Hello World!  RATUL</h1>
    </body>
</html>-->


<html>
    <head>
        <title>Facebook Login JavaScript Example</title>
        <meta charset="UTF-8">
    </head>
    <body>

        <script>
            window.fbAsyncInit = function () {
                FB.init({
                    appId: '1938557403035145',
                    xfbml: true,
                    version: 'v2.5'
                });
            };

            (function (d, s, id) {
                var js, fjs = d.getElementsByTagName(s)[0];
                if (d.getElementById(id)) {
                    return;
                }
                js = d.createElement(s);
                js.id = id;
                js.src = "//connect.facebook.net/en_US/sdk.js";
                fjs.parentNode.insertBefore(js, fjs);
            }(document, 'script', 'facebook-jssdk'));
        </script>
        <div
  class="fb-like"
  data-share="true"
  data-width="450"
  data-show-faces="true">
</div>
        <script>
            // This is called with the results from from FB.getLoginStatus().
            function statusChangeCallback(response) {
                console.log('statusChangeCallback');
                console.log(response);
                // The response object is returned with a status field that lets the
                // app know the current login status of the person.
                // Full docs on the response object can be found in the documentation
                // for FB.getLoginStatus().
                if (response.status === 'connected') {
                    // Logged into your app and Facebook.
                    testAPI();
                } else if (response.status === 'not_authorized') {
                    // The person is logged into Facebook, but not your app.
                    document.getElementById('status').innerHTML = 'Please log ' +
                            'into this app.';
                } else {
                    // The person is not logged into Facebook, so we're not sure if
                    // they are logged into this app or not.
                    document.getElementById('status').innerHTML = 'Please log ' +
                            'into Facebook.';
                }
            }

            // This function is called when someone finishes with the Login
            // Button.  See the onlogin handler attached to it in the sample
            // code below.
            function checkLoginState() {
                FB.getLoginStatus(function (response) {
                    statusChangeCallback(response);
                });
            }

            window.fbAsyncInit = function () {
                FB.init({
                    appId: '{your-app-id}',
                    cookie: true, // enable cookies to allow the server to access 
                    // the session
                    xfbml: true, // parse social plugins on this page
                    version: 'v2.2' // use version 2.2
                });

                // Now that we've initialized the JavaScript SDK, we call 
                // FB.getLoginStatus().  This function gets the state of the
                // person visiting this page and can return one of three states to
                // the callback you provide.  They can be:
                //
                // 1. Logged into your app ('connected')
                // 2. Logged into Facebook, but not your app ('not_authorized')
                // 3. Not logged into Facebook and can't tell if they are logged into
                //    your app or not.
                //
                // These three cases are handled in the callback function.

                FB.getLoginStatus(function (response) {
                    statusChangeCallback(response);
                });

            };

            // Load the SDK asynchronously
            (function (d, s, id) {
                var js, fjs = d.getElementsByTagName(s)[0];
                if (d.getElementById(id))
                    return;
                js = d.createElement(s);
                js.id = id;
                js.src = "//connect.facebook.net/en_US/sdk.js";
                fjs.parentNode.insertBefore(js, fjs);
            }(document, 'script', 'facebook-jssdk'));

            // Here we run a very simple test of the Graph API after login is
            // successful.  See statusChangeCallback() for when this call is made.
            function testAPI() {
                console.log('Welcome!  Fetching your information.... ');
                FB.api('/me', function (response) {
                    console.log('Successful login for: ' + response.name);
                    document.getElementById('status').innerHTML =
                            'Thanks for logging in, ' + response.name + '!';
                });
            }
        </script>


        Below we include the Login Button social plugin. This button uses
        the JavaScript SDK to present a graphical Login button that triggers
        the FB.login() function when clicked.


    <fb:login-button scope="public_profile,email" onlogin="checkLoginState();">
    </fb:login-button>

    <div id="status" >
    </div>

</body>
</html>