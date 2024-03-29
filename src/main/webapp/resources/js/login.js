// 로그인 아이디 비밀번호 입력확인
function formCheck(frm) {
    if(frm.id.value.length===0) {
        setMessage('id를 입력해주세요.', frm.id);
        return false;
    }
    if(frm.pwd.value.length===0) {
        setMessage('password를 입력해주세요.', frm.pwd);
        return false;
    }
    return true;
}

// 에러메시지 표시
function setMessage(msg, element){
    $("#msg").html(msg);
    if(element) {
        element.select();
    }
}

// 비밀번호 찾기 창 자바스크립트

let modal = $("#myModal");
let btn = $("#myBtn");
let span = $(".close").eq(0);

btn.on("click", function () {
    modal.css("display", "block");
});

span.on("click", function () {
    modal.css("display", "none");
});

$(window).on("click", function (event) {
    if (event.target === modal[0]) {
        modal.css("display", "none");
    }
});

// 회원정보 전송하여 해당회원이 있는지 확인
$(document).on('click', '#pwdSer', function () {

    let userInfo = {
        id : $("#userId").val(),
        name : $("#userName").val(),
        email : $("#userEmail").val()
    };

    console.log(userInfo);

    if(userInfo.id.trim()===''){
        alert("아이디를 입력해주세요");
        $("#userId").focus()
        return;
    }

    if(userInfo.name.trim()===''){
        alert("이름을 입력해주세요");
        $("#userName").focus()
        return;
    }

    if(userInfo.email.trim()===''){
        alert("이메일을 입력해주세요");
        $("userEmail").focus()
        return;
    }

    $.ajax({
        type:'POST',       // 요청 메서드
        url: '/login/pwd',
        headers : { "content-type": "application/json"}, // 요청 헤더
        data : JSON.stringify(userInfo),  // 서버로 전송할 데이터. stringify()로 직렬화 필요.
        success : function(result){
            // let result1 = JSON.parse(result);
            // console.log(result1);
            console.log(result);
            alert("계정정보 확인완료");
            let id = $(result).find('id').text();
            $("#reId").val(id);
            modal.css("display", "block");
            $(".modal-content").css("height", "600px");
            $("#rePwdDiv").show();
        },
        error   : function(result){
            console.log(result.status);
            if(result.status === 401){
                alert("관리자 계정입니다 비밀번호 변경이 불가능합니다.");
            } else {
                alert("해당되는 계정이 존재하지 않습니다.");
            }
            init();
            $("#rePwdDiv").hide();
        }
    });
});


// 함수를 만들어 비밀번호와 비밀번호 확인이 같은지 검사
function checkPassword() {
    if (pwdField.val() === pwdConfirmField.val()) {
        checkSpan.show();
        $("#pwdChk").val("Y");
    } else {
        checkSpan.hide();
        $("#pwdChk").val("N");
    }
}

// 비밀번호 입력 시 검사
pwdField.on('input', checkPassword);
pwdConfirmField.on('input', checkPassword);

$(document).on('click', '#rePwdSubmit', function () {

    let userInfo = {
        pwd : $("#rePwd").val(),
        id : $("#reId").val()
    };

    const isValidPassword = /^(?=.*[a-zA-Z])(?=.*\d)(?=.*[!@#$%^&*])[a-zA-Z\d!@#$%^&*]{6,12}$/.test(userInfo.pwd);
    if (!isValidPassword) {
        alert("패스워드는 6~12자리 영대소문자와 숫자와 특수문자 조합이어야합니다.");
        return false;
    }

    let pwdChk = $("#pwdChk").val();
    console.log(pwdChk);
    if(pwdChk!=="Y") {
        alert("비밀번호 확인이 필요합니다.")
        return false;
    }

    $.ajax({
        type:'POST',       // 요청 메서드
        url: '/login/change',
        headers : { "content-type": "application/json"}, // 요청 헤더
        data : JSON.stringify(userInfo),  // 서버로 전송할 데이터. stringify()로 직렬화 필요.
        success : function(){
            alert("비밀번호 변경에 성공했습니다!");
            $("#rePwdDiv").hide();
            modal.css("display", "none");
            init();
        },
        error   : function(){ alert("비밀번호 변경에 실패하였습니다.") } // 에러가 발생했을 때, 호출될 함수
    }); // $.ajax()

});

// 비밀번호 찾기창 초기화
function init(){
    $('input').each(function() {
        $(this).val('');
    });
}
