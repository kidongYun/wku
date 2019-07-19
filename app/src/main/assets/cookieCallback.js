function cookieFunc(cookieStr) {
    window.JSInterface.callMethodName(cookieStr);
}

function initIDPW(id, pw) {
  idInput = document.getElementById('userid');
  pwInput = document.getElementById('passwd');
  loginBtn = document.getElementById('f_login');

  idInput.value = id;
  pwInput.value = pw;

  loginBtn.submit();
}
