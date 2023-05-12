function formCheck(frm) {
    let msg ='';
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

function setMessage(msg, element){
    document.getElementById("msg").innerHTML = `${'${msg}'}`;
    if(element) {
        element.select();
    }
}

// 비밀번호 찾기 창 자바스크립트

// Get the modal
let modal = document.getElementById("myModal");

// Get the button that opens the modal
let btn = document.getElementById("myBtn");

// Get the <span> element that closes the modal
let span = document.getElementsByClassName("close")[0];

// When the user clicks the button, open the modal
btn.onclick = function() {
    modal.style.display = "block";
}

// When the user clicks on <span> (x), close the modal
span.onclick = function() {
    modal.style.display = "none";
}

// When the user clicks anywhere outside of the modal, close it
window.onclick = function(event) {
    if (event.target === modal) {
        modal.style.display = "none";
    }
}


$("#pwdSer").click(function(){

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
            alert("계정정보 확인완료");
            let id = $(result).find('id').text();
            $("#reId").val(id);
            modal.style.display = "block";
            $("#rePwdDiv").show();
        },
        error   : function(){
            alert("해당되는 계정이 존재하지 않습니다.")
            init();
            $("#rePwdDiv").hide();
        } // 에러가 발생했을 때, 호출될 함수
    }); // $.ajax()

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



$("#rePwdSubmit").click(function (){

    let userInfo = {
        pwd : $("#rePwd").val(),
        id : $("#reId").val()
    };

    console.log(userInfo);

    let ID_RE = /^(?=.*[a-zA-Z])(?=.*\d)(?=.*[!@#$%^&*])[a-zA-Z\d!@#$%^&*]{6,12}$/;
    if(!(ID_RE.test(userInfo.pwd))) {
        alert("비밀번호는 6~12자리 특수문자와 영문자 그리고 숫자 조합이어야합니다.")
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
        success : function(result){
            alert("비밀번호 변경에 성공했습니다!");
            $("#rePwdDiv").hide();
            modal.style.display = "none";
            init();
        },
        error   : function(){ alert("비밀번호 변경에 실패하였습니다.") } // 에러가 발생했을 때, 호출될 함수
    }); // $.ajax()

});


function init(){
    $('input').each(function() {
        $(this).val('');
    });
}
