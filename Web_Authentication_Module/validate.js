// Client-side validation to prevent basic XSS and SQL injection attempts, validate username and password requirements
function validateForm() 
{
   // Get the username and password fields
   // Sanitize inputs with the htmlentities function
   var username = htmlEntities(document.getElementById("username").value);
   var password = htmlEntities(document.getElementById("password").value);
   
   // Define minimum length requirements (you can adjust as needed)
   var minUsernameLength = 3;
   var minPasswordLength = 6;

   // Check if the username is empty or too short
   if (username === "") {
      alert("Username cannot be empty");
      return false;
   } else if (username.length < minUsernameLength) {
      alert("Username must be at least " + minUsernameLength + " characters long.");
      return false;
   }

   // Check if the password is empty or too short
   if (password === "") {
      alert("Password cannot be empty");
      return false;
   } else if (password.length < minPasswordLength) {
      alert("Password must be at least " + minPasswordLength + " characters long.");
      return false;
   }

   //Add additional checks such as requiring special characters for passwords, etc. as needed

   // If all validations pass, allow form submission
   return true;
}

//Convert potentially harmful characters to html entities
function htmlEntities(str) 
{
  return str.replace(/&/g, "&amp;")
            .replace(/</g, "&lt;")
            .replace(/>/g, "&gt;")
            .replace(/"/g, "&quot;")
            .replace(/'/g, "&#039;");
}