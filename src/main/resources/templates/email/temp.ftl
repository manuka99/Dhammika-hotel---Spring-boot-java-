<html> 
<head></head>
 
<body>
    <p>Dear ${username},</p>
    <p>Sending Email using Spring Boot with <b>FreeMarker template !!!</b></p>
    <p>Thanks</p>
    <p>${signature}</p>
    <p>${location}</p>
    <#if userName??>
   Hi ${userName}, How are you?
</#if>
    <#if username??>
   Hi ${username}, How are you?
</#if>
</body>
 
</html>


