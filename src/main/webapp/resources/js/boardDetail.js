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
        if(imgSrc) {
            $("#image").show();
            $("#mainText").css("width", "49%");
        } else {
            $("#image").hide();
            $("#mainText").css("width", "100%");
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


$("#files").change(function() {
    let file = $("#files")[0].files[0];
    let file_RE = /(\.jpg|\.jpeg|\.png|\.gif)$/i;
    if (!file_RE.exec(file.name)) {
        alert("허용되지 않는 파일 형식입니다. (jpg, jpeg, png, gif 파일만 첨부 가능합니다.)");
        $("#files").val("");
        return false;
    }
    if (file.size > 2 * 1024 * 1024) {
        alert("파일 크기는 최대 2MB까지 업로드 가능합니다.");
        $("#files").val("");
        return false;
    }

    previewImage(event);

});


function previewImage(event) {
    const input = event.target;
    const reader = new FileReader();
    reader.onload = function () {
        const img = document.getElementById("preview-image");
        img.src = reader.result;
    };
    reader.readAsDataURL(input.files[0]);
}

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