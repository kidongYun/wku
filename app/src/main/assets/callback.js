
function callAndroid(fgubun) {
    var str = fgubun;
    window.JSInterface.callMethodName(str);
}

function downGo(fgubun)
{
  // var form = document.view;
  //
  // form.fgubun.value = fgubun;
  // form.isRealPath.value = 'T';
  // form.baseSavePath.value = '/wupis/cyber/ComBoard/upload/upload';
  // form.method = 'POST';
  // form.action = '/Cyber/ComBoardDownLoad';
  // form.submit();

  callAndroid(fgubun);
}
