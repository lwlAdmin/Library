$(function(){
    $("#next").click(function(){
        let pages = parseInt($("#pages").html());
        let page = parseInt($("#currentPage").html());
        if(page == pages){
            alert("已经是最后一页了")
            return;
        }
        page++;
        location.href = "/admin?method=getBorrowed&page="+page;
    })

    $("#previous").click(function () {
        let page = parseInt($("#currentPage").html());
        if(page == 1){
            return;
        }
        page--;
        location.href = "/admin?method=getBorrowed&page="+page;
    })

    $("#first").click(function () {
        location.href = "/admin?method=getBorrowed&page=1";
    })

    $("#last").click(function(){
        let pages = parseInt($("#pages").html());
        location.href = "/admin?method=getBorrowed&page="+pages;
    })
})