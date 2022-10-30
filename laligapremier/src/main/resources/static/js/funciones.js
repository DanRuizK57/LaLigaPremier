function checkPasswordMatch(fieldConfirmPassword) {
    if (fieldConfirmPassword.val() !== $("#clave").val()) {
        fieldConfirmPassword.setCustomValidity("¡Las contraseñas deben coincidir!");
    } else {
        fieldConfirmPassword.setCustomValidity("");
    }
}