$(document).ready(function(){
    let formCheck = function() {
        let form = document.getElementById("form");
        if(form.title.value==="") {
            alert("제목을 입력해 주세요.");
            form.trigger( "focus" );
            // form.title.focus();
            return false;
        }
        if(form.content.value==="") {
            alert("내용을 입력해 주세요.");
            form.content.focus();
            return false;
        }
        return true;
    }

    $(document).ready(function() {
        let imgSrc = $("#image").attr("src");
        if(imgSrc) {
            $("#image").show();
            $("#mainText").css("width", "49%");
        } else {
            $("#image").hide();
            $("#mainText").css("width", "100%");
        }
    });

    $(document).on("click", "#writeNewBtn", function() {
        location.href="<c:url value='/board/write'/>";
    });

    $(document).on("click", "#modifyBtn", function() {
        let form = $("#form");
        let isReadonly = $("input[name=title]").attr('readonly');
        if(isReadonly=='readonly') {
            $('#modeDiv').show();
            $(".writing-header").html("게시판 수정");
            $("input[name=title]").attr('readonly', false);
            $("textarea").attr('readonly', false);
            $("#modifyBtn").html("<i class='fa fa-pencil'></i> 등록");
            $("#writeBtn").hide();
            $(".mb-5").hide();
            $("#recommned").hide();
            return;
        }
        // 2. 수정 상태이면, 수정된 내용을 서버로 전송
        form.attr("action", modifyUrl);
        form.attr("method", "post");
        if(formCheck())
            form.trigger( "submit" );
            // form.submit();
    });
    $(document).on("click", "#deleteBtn", function() {
        if(!confirm("정말로 삭제하시겠습니까?")) return;
        let form = $("#form");
        form.attr("action", deleteUrl);
        form.attr("method", "post");
        form.trigger( "submit" );
    });
    $(document).on("click", "#listBtn", function() {
        location.href=listUrl;
    });
});


// 중복 추천 여부를 확인하는 함수
function hasLiked() {
    const liked = localStorage.getItem(localName); // 로컬 스토리지에서 해당 아이디의 추천 여부 가져오기

    if (liked === null) {
        return false;
    }

    return liked === "true" ;
}

// 좋아요 버튼 클릭 이벤트 핸들러
function Boardlike() {
    if (hasLiked()) {
        alert("이미 추천하셨습니다!");
        return;
    }

    $.ajax({
        url: "/board/like",
        type: "GET",
        data: { like : bnoVal },
        success: function(response) {
            console.log(response);
            likeCount++;
            likeCnt();
        },
        error: function(error) {
            console.error(error);
        }
    });

    localStorage.setItem(localName, "true");
}


