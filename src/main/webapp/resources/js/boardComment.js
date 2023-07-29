function spreadCommentFromServer(bno) {
    if (bno == null) {
        return false;
    }
    console.log(bno);
    return $.ajax({
        url: "/comments",
        type: "GET",
        data: {
            bno: bno
        },
        dataType: "json"
    });
}


// 로컬 sql 댓글 작성 

// function getCommentList(bno,loginId) {
//     spreadCommentFromServer(bno)
//         .done(function(result) {
//             let $div = $("#Comment");
//             $div.empty();
//             let html = "<ul>";
//             for (let i = 0; i < result.length; i++) {
//                 html += "<li>";
//                 html += `<div class="d-flex" data-pcno="${result[i].pcno}" data-cno="${result[i].cno}" data-bno="${result[i].bno}">`;
//                 if(result[i].cno !== result[i].pcno) {
//                     html += `<div style="font-size: 20px;">ㄴ</div>`;
//                 }
//                 const FilePath = result[i].image_file ? result[i].image_file.replace(/\\/g, "/") : "no-image.png";
//                 html += `<div class="flex-shrink-0"><img class="rounded-circle" src="/fileUpload/${FilePath}" alt="프로필 이미지" /></div>`;
//                 html += `<div class="ms-3" style="width: 700px">`;
//                 html += `<div class="fw-bold" id="cmtWriter1" >${result[i].writer}</div>`;
//                 html += `${result[i].content}`;
//                 html += `<div STYLE="text-align: right">`;
//                 if(sessionId!==""){
//                     html +=  `<button type="button" id="cmtReplyBtn" class="btn btn-sm btn-outline-primary ">답글</button>`;
//                 }
//                 if (result[i].writer === loginId) {
//                     html += `<button type="button" id="cmtDelBtn" class="btn btn-sm btn-outline-danger ">삭제</button>`;
//                 }
//                 html += `</div>`;
//                 html += `</div></div><br></li><hr>`;
//             }
//             html += "</ul>";
//             $div.append(html);
//         })
//         .fail(function(jqXHR, textStatus, errorThrown) {
//             console.log(textStatus + ": " + errorThrown);
//         });
// }

// azure 댓글 작성

function getCommentList(bno, loginId) {
    spreadCommentFromServer(bno)
        .done(function (result) {
            let $div = $("#Comment");
            $div.empty();
            let html = "<ul>";
            for (let i = 0; i < result.length; i++) {
                html += "<li>";
                html += `<div class="d-flex" data-pcno="${result[i].pcno}" data-cno="${result[i].cno}" data-bno="${result[i].bno}">`;
                if (result[i].cno !== result[i].pcno) {
                    html += `<div style="font-size: 20px;">ㄴ</div>`;
                }
                // 프로필 사진 등록 여부에 따라 기본 프로필 사진 혹은 등록된 프로필이 나온다
                if (result[i].image_file != null) {
                    const FileName = result[i].image_file ? result[i].image_file.replace(/\\/g, "/") : "no-image.png";
                    const ImageUrl = `https://myboard.blob.core.windows.net/youngmokboard/${FileName}`;
                    html += `<div class="flex-shrink-0"><img class="rounded-circle" style="height: 75px; width: 75px;" src="${ImageUrl}" alt="프로필 이미지" /></div>`;
                } else {
                    const DefaultImageUrl = "/resources/img/user.png";
                    html += `<div class="flex-shrink-0"><img class="rounded-circle" style="height: 75px; width: 75px;" src="${DefaultImageUrl}" alt="프로필 이미지" /></div>`;
                }
                html += `<div class="ms-3" style="width: 700px">`;
                html += `<div class="fw-bold" id="cmtWriter1" >${result[i].writer}</div>`;
                html += `${result[i].content}`;
                html += `<div STYLE="text-align: right">`;
                if (sessionId !== "") {
                    html += `<button type="button" id="cmtReplyBtn" class="btn btn-sm btn-outline-primary ">답글</button>`;
                }
                if (result[i].writer === loginId) {
                    html += `<button type="button" id="cmtDelBtn" class="btn btn-sm btn-outline-danger ">삭제</button>`;
                }
                html += `</div>`;
                html += `</div></div><br></li><hr>`;
            }
            html += "</ul>";
            $div.append(html);
        })
        .fail(function (jqXHR, textStatus, errorThrown) {
            console.log(textStatus + ": " + errorThrown);
        });
}

async function postCommentToServer(Comment) {
    console.log(Comment);
    try {
        const url = '/comments';
        const config = {
            method: 'POST',
            headers: {'Content-Type': 'application/json; charset=utf-8'},
            data: JSON.stringify(Comment)
        };
        return await $.ajax(url, config);
    } catch (error) {
        console.log(error);
    }
}


$(document).on('click', '#cmtPostBtn', async () => {
    $('#replyForm').appendTo('body');
    const cmtText = $('#cmtText').val();
    console.log(cmtText);
    if (cmtText == null || cmtText === '') {
        alert('댓글을 입력해주세요');
        $('#cmtText').focus();
        return false;
    } else {
        let Comment = {
            bno: bnoVal,
            writer: $('#cmtWriter').text(),
            content: cmtText
        };
        if (Comment.writer === '' || Comment.writer === null) {
            alert("세션오류 로그아웃이 되었습니다.");
            window.location.href = "/";
            return false;
        }
        console.log(Comment);
        const result = await postCommentToServer(Comment);
        if (result > 0) {
            alert('댓글등록성공');
        }
        await getCommentList(bnoVal, login);
        $('#cmtText').val('');
    }
});
// $(document).on("click", "#cmtReplyBtn", function() {
$("#Comment").on("click", "#cmtReplyBtn", function () {
    // 1 replyForm을 옮기고
    $(this).parent().append($("#replyForm"));
    // 2 답글을 입력할 폼을 보여줌
    $("#replyForm").show();
});

$("#Comment").on("click", "#cmtDelBtn", function () {
    if (!confirm("댓글을 삭제하시겠습니까?")) {
        alert("댓글삭제를 취소하였습니다.");
        return false;
    }
    $("#replyForm").appendTo("body");

    const cno = $(this).closest('.d-flex').data('cno');

    $.ajax({
        type: 'DELETE',
        url: '/comments/' + cno + '?bno=' + bnoVal,
        //  생략시 자동으로 json
        success: function (result) {
            alert("댓글이 삭제되었습니다.");
            getCommentList(bnoVal, login);
        },
        error: function () {
            alert("error")
        }
    });
});


$(document).on("click", "#cmtReplybtn", function () {

    let Comment = {
        content: $("#replyText").val(),
        bno: bnoVal,
        pcno: $(this).closest('.d-flex').data('pcno')
    };

    console.log(Comment);

    if (Comment.content.trim() === '') {
        alert("댓글을 입력해주세요");
        $("#replyText").focus()
        return;
    }

    $.ajax({
        type: 'POST',
        url: '/comments',
        headers: {"content-type": "application/json"},
        data: JSON.stringify(Comment),
        success: function (result) {
            getCommentList(bnoVal, login);
        },
        error: function () {
            alert("error")
        }
    }); // $.ajax()

    $("#replyText").val('');
    $("#replyForm").hide();
    $("#replyForm").appendTo("body");

});

