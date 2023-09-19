// 아이디 유효성 검사
function isValidUserId(id) {
    const isValid = /^[a-zA-Z0-9]{5,12}$/.test(id);
    if (!isValid) {
        setMessage('id는 5~12자리 영대소문자 숫자 조합이어야합니다.', id);
    }
    return isValid;
}

//회원가입 폼 체크
function registerFormCheck(frm) {
    let msg ='';

    // 아이디 중복 체크 확인
    let chk = $("#idChk").val();
    console.log(chk);
    if(chk !== "Y") {
        setMessage('아이디 중복체크가 필요합니다', frm.id);
        return false;
    }

    // 비밀번호 확인 확인
    let pwdChk = $("#pwdChk").val();
    console.log(pwdChk);
    if(pwdChk !== "Y") {
        setMessage('비밀번호 확인이 필요합니다', frm.pwd-confirm); // frm.pwd-confirm가 정확한지 확인 필요
        return false;
    }

    if(!isValidUserId(frm.id.value)){
        return false;
    }
    
    // 다른 필드들의 유효성 검사
    formCheck(frm);

    return formCheck(frm);
}

// 회원정보 폼 체크
function modFormCheck(frm){
    let msg ='';
    return formCheck(frm);
}

// 나머지 필드 유효성검사
function formCheck(frm){
    // 패스워드 유효성 검사
    const isValidPassword = /^(?=.*[a-zA-Z])(?=.*\d)(?=.*[!@#$%^&*])[a-zA-Z\d!@#$%^&*]{6,12}$/.test(frm.pwd.value);
    if (!isValidPassword) {
        setMessage('패스워드는 6~12자리 영대소문자와 숫자와 특수문자 조합이어야합니다.', frm.pwd);
    }

    // 이름 유효성 검사
    const isValidName = /^[가-힣]{2,6}|[a-zA-Z]{2,10}\s[a-zA-Z]{2,10}$/.test(frm.name.value);
    if (!isValidName) {
        setMessage('이름은 2~6글자 한글이름 혹은 영문이름이어야합니다.', frm.name);
    }

    // 이메일 유효성 검사
    const isValidEmail = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/.test(frm.email.value);
    if (!isValidEmail) {
        setMessage('이메일형식이 맞지 않습니다.', frm.email);
    }

    // 날짜 선택 여부 확인
    const birthValue = $("#birth").val();
    if (!birthValue) {
        setMessage('날짜를 선택해주세요.', birthValue);
    }

    return isValidPassword && isValidName && isValidEmail && birthValue; // 하나라도 false 있다면 검사실패
}

// 아이디 중복 체크
function fn_idChk(){
    let id = $("#id").val();

    if(!isValidUserId(id)){
        return false;
    }

    console.log(id);

    // 아이디 중복 확인을 위한 Ajax 요청
    $.ajax({
        url : "/register/idChk",
        type : "post",
        contentType: "application/json",
        data : JSON.stringify({ "id" : id }),
        dataType: "json",
        success : function(data){
            console.log(data);
            if(data === 1){  // 아이디가 중복될 경우
                $("#idChk").attr("value", "N");
                $("#username-check").addClass("hidden");
                $("#idChk").css("background-color", "black");
                alert("중복된 아이디입니다.");
            }else if(data === 0){  // 아이디가 중복되지 않을 경우
                $("#idChk").attr("value", "Y");
                $("#idChk").css("background-color", "blue");
                $("#username-check").removeClass("hidden");
                alert("사용가능한 아이디입니다.");
            }
        },
        error : function (){
            console.log("아이디 중북체크 에러발생");
            alert("아이디 중복체크가 실패하였습니다.");
        }
    });
}

// 아이디 중복확인 후 아이디 값이 바뀌었을 시 다시 체크하도록..
$("#id").on("input", function() {
    idChange(); // 입력 값이 변경될 때마다 idChange() 함수 호출
});

// 아이디 입력값이 변경될 때 상태를 다시 'N'으로 설정
function idChange() {
    $("#idChk").val('N');
    $("#username-check").addClass("hidden");
}

// 에러시 메시지
function setMessage(msg, element){
    $("#msg").html(`<i class="fa fa-exclamation-circle"> ${msg}</i>`);
    if(element) {
        element.select();
    }
}

// 비밀번호 재확인을 위한 변수들
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

// 생일 입력시 오늘 날짜 이후로는 선택불가능하도록
function getTodayDate() {
    const now = new Date();
    const year = now.getFullYear();
    const month = String(now.getMonth() + 1).padStart(2, '0');
    const day = String(now.getDate()).padStart(2, '0');
    console.log(year+'-'+month+'-'+day)
    return year+'-'+month+'-'+day;
}
$(document).ready(function() {
    const dateInput = $('#birth');
    dateInput.attr('max', getTodayDate());
});