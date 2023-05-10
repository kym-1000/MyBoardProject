function registerFormCheck(frm) {
    let msg ='';

    let chk = $("#idChk").val();
    console.log(chk);
    if(chk!=="Y") {
        setMessage('아이디 중복체크가 필요합니다', frm.id);
        return false;
    }
    let pwdChk = $("#pwdChk").val();
    console.log(chk);
    if(pwdChk!=="Y") {
        setMessage('비밀번호 확인이 필요합니다', frm.pwd-confirm);
        return false;
    }

    let ID_RE = /^[a-zA-Z0-9]{5,12}$/;
    if(!(ID_RE.test(frm.id.value))) {
        setMessage('id는 5~12자리 영대소문자 숫자 조합이어야합니다.', frm.id);
        return false;
    }

    formCheck(frm);

    return formCheck(frm);
}

function modFormCheck(frm){
    let msg ='';

    return formCheck(frm);

}

function formCheck(frm){

    let pwd_RE = /^(?=.*[a-zA-Z])(?=.*\d)(?=.*[!@#$%^&*])[a-zA-Z\d!@#$%^&*]{6,12}$/;
    if(!(pwd_RE.test(frm.pwd.value))) {
        setMessage('패스워드는 6~12자리 영대소문자 특수문자 및 숫자 조합이어야합니다.', frm.pwd);
        return false;
    }

    let name_RE = /^[가-힣]{2,6}|[a-zA-Z]{2,10}\s[a-zA-Z]{2,10}$/;
    if(!(name_RE.test(frm.name.value))) {
        setMessage('이름은 2~6글자 한글이름 혹은 영문이름이어야합니다.', frm.name);
        return false;
    }

    let email_RE = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
    if(!(email_RE.test(frm.email.value))) {
        setMessage('이메일형식이 맞지 않습니다.', frm.email);
        return false;
    }

    if($("#birth").val() === '') {
        setMessage('날짜를 선택해주세요.', $('#birth').val());
        return false;
    }
    return true;
}


$("#file").change(function() {
    let file = $("#file")[0].files[0];
    let file_RE = /(\.jpg|\.jpeg|\.png|\.gif)$/i;
    if (!file_RE.exec(file.name)) {
        alert("허용되지 않는 파일 형식입니다. (jpg, jpeg, png, gif 파일만 첨부 가능합니다.)");
        $("#file").val("");
        return false;
    }
});

function fn_idChk(){
    let id = $("#id").val();

    let ID_RE = /^[a-zA-Z0-9]{5,12}$/;
    if(!(ID_RE.test(id))) {
        setMessage('id는 5~12자리 영대소문자 숫자 조합이어야합니다.', id);
        return false;
    }

    console.log(id);

    $.ajax({
        url : "/register/idChk",
        type : "post",
        dataType : "json",
        data : {"id" : id},
        success : function(data){
            if(data === 1){
                $("#idChk").attr("value", "N");
                $("#username-check").addClass("hidden");
                alert("중복된 아이디입니다.");
            }else if(data === 0){
                $("#idChk").attr("value", "Y");
                $("#username-check").removeClass("hidden");
                alert("사용가능한 아이디입니다.");
            }
        }
    })
}

function setMessage(msg, element){
    document.getElementById("msg").innerHTML = `<i class="fa fa-exclamation-circle"> ${msg}</i>`;
    if(element) {
        element.select();
    }
}

function previewImage(event) {
    const input = event.target;
    const reader = new FileReader();
    reader.onload = function () {
        const img = document.getElementById("preview-image");
        img.src = reader.result;
    };
    reader.readAsDataURL(input.files[0]);
}

const pwdField = $('input[name="pwd"]');
const pwdConfirmField = $('input[name="pwd-confirm"]');
const checkSpan = $('#userpwd-check');

// 함수를 만들어 비밀번호와 비밀번호 확인이 같은지 검사
function checkPassword() {
    if (pwdField.val() === pwdConfirmField.val()) {
        checkSpan.removeClass('hidden');
        $("#pwdChk").val("Y");
    } else {
        checkSpan.addClass('hidden');
        $("#pwdChk").val("N");
    }
}

// 비밀번호 입력 시 검사
pwdField.on('input', checkPassword);
pwdConfirmField.on('input', checkPassword);

