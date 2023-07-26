
if(msg.includes("BOARD")){
    if(msg==="BOARD_WRT_ERR") alert("게시물 등록에 실패하였습니다. 다시 시도해 주세요.");
    if(msg==="BOARD_MOD_ERR") alert("게시물 수정에 실패하였습니다. 다시 시도해 주세요.");
    if(msg==="BOARD_LIST_ERR")  alert("게시물 목록을 가져오는데 실패했습니다. 다시 시도해 주세요.");
    if(msg==="BOARD_READ_ERR")  alert("삭제되었거나 없는 게시물입니다.");
    if(msg==="BOARD_DEL_ERR")   alert("삭제되었거나 없는 게시물입니다.");
    if(msg==="BOARD_DEL_OK")    alert("성공적으로 삭제되었습니다.");
    if(msg==="BOARD_WRT_OK")    alert("성공적으로 등록되었습니다.");
    if(msg==="BOARD_MOD_OK")    alert("성공적으로 수정되었습니다.");
} else if(msg.includes("USER")){
    if(msg==="USER_DEL_OK")    alert("계정이 삭제되었습니다.");
    if(msg==="USER_DEL_ERR")    alert("계정삭제가 실패하였습니다.");
    if(msg==="USER_MOD_OK")    alert("회원정보 수정이 성공하였습니다.");
    if(msg==="USER_MOD_ERR")    alert("회원정보 수정이 실패하였습니다.");
    if(msg==="USER_JOIN_OK")    alert("회원가입성공!");
    if(msg==="USER_JOIN_FAIL")    alert("회원가입실패!");
    if(msg==="USER_INFO_FAIL") alert("회원정보를 불러오지못하였습니다.");
    if(msg==="USER_LOGIN_FAIL") alert("로그인을 실패하였습니다.");
} else{
    if(msg==="SESSION_ERR")    alert("세션이 풀렸습니다 다시 로그인 해주세요.");
}



