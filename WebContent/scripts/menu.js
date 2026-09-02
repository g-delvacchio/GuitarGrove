const sidebar = document.getElementById("sidebar");
const overlay = document.getElementById("overlay");
const openBtn = document.getElementById("openMenu");
const closeBtn = document.getElementById("closeMenu");

function openMenu(){
    sidebar.classList.add("active");
    overlay.classList.add("active");
}

function closeMenu(){
    sidebar.classList.remove("active");
    overlay.classList.remove("active");
}

openBtn.addEventListener("click", openMenu);
closeBtn.addEventListener("click", closeMenu);
overlay.addEventListener("click", closeMenu);