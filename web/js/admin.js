$(function(){
    $("#next").click(function(){
        var pages = parseInt($("#pages").html());
        var page = parseInt($("#currentPage").html());
        if(page == pages){
            alert("已经是最后一页了")
            return;
        }
        page++;
        location.href = "/admin?page="+page;
    })

    $("#previous").click(function () {
        var page = parseInt($("#currentPage").html());
        if(page == 1){
            return;
        }
        page--;
        location.href = "/admin?page="+page;
    })

    $("#first").click(function () {
        location.href = "/admin?page=1";
    })

    $("#last").click(function(){
        var pages = parseInt($("#pages").html());
        location.href = "/admin?page="+pages;
    })
})