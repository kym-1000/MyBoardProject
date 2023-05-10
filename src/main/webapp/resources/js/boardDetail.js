$(document).ready(function(){
    let formCheck = function() {
        let form = document.getElementById("form");
        if(form.title.value==="") {
            alert("제목을 입력해 주세요.");
            form.title.focus();
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
        if(imgSrc) { // 이미지가 있는 경우
            $("#image").show(); // 이미지 보이기
            $("#mainText").css("width", "49%"); // 텍스트 에어리어 너비 조정
        } else { // 이미지가 없는 경우
            $("#image").hide(); // 이미지 숨기기
            $("#mainText").css("width", "100%"); // 텍스트 에어리어 너비 조정
        }
    });

    $("#writeNewBtn").on("click", function(){
        location.href="<c:url value='/board/write'/>";
    });

    $("#modifyBtn").on("click", function(){
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
            return;
        }
        // 2. 수정 상태이면, 수정된 내용을 서버로 전송
        form.attr("action", modifyUrl);
        form.attr("method", "post");
        if(formCheck())
            form.submit();
    });
    $("#deleteBtn").on("click", function(){
        if(!confirm("정말로 삭제하시겠습니까?")) return;
        let form = $("#form");
        form.attr("action", deleteUrl);
        form.attr("method", "post");
        form.submit();
    });
    $("#listBtn").on("click", function(){
        location.href=listUrl;
    });
});


// <%--    form.attr("action", "<c:url value='/board/modify${searchCondition.queryString}'/>");--%>
// <%--    form.attr("method", "post");--%>
// <%--    if(formCheck())--%>
// <%--      form.submit();--%>
// <%--  });--%>
// <%--  $("#deleteBtn").on("click", function(){--%>
// <%--    if(!confirm("정말로 삭제하시겠습니까?")) return;--%>
// <%--    let form = $("#form");--%>
// <%--    form.attr("action", "<c:url value='/board/boardDelete${searchCondition.queryString}'/>");--%>
// <%--    form.attr("method", "post");--%>
// <%--    form.submit();--%>
// <%--  });--%>
// <%--  $("#listBtn").on("click", function(){--%>
// <%--    location.href="<c:url value='/board/list${searchCondition.queryString}'/>";--%>