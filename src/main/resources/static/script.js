const API = "/api/v1/verses/search"

const form = document.getElementById("searchForm")
const keywordInput = document.getElementById("keyword")

const results = document.getElementById("results")

const prevBtn = document.getElementById("prevBtn")
const nextBtn = document.getElementById("nextBtn")

const pageInfo = document.getElementById("pageInfo")

const spinner = document.getElementById("spinner")

let currentPage = 0
let totalPages = 0
let currentKeyword = ""

form.addEventListener("submit", function(e){

e.preventDefault()

currentKeyword = keywordInput.value
currentPage = 0

searchVerses()

})

prevBtn.addEventListener("click",function(){

if(currentPage > 0){

currentPage--

searchVerses()

}

})

nextBtn.addEventListener("click",function(){

if(currentPage < totalPages-1){

currentPage++

searchVerses()

}

})

async function searchVerses(){

spinner.classList.remove("hidden")

const response = await fetch(
`${API}?keyword=${currentKeyword}&page=${currentPage}&size=10`
)

const data = await response.json()

displayVerses(data.content)

totalPages = data.totalPages

pageInfo.innerText = `Page ${data.number + 1} of ${data.totalPages}`

spinner.classList.add("hidden")

}

function displayVerses(verses){

results.innerHTML = ""

verses.forEach(v => {

const div = document.createElement("div")

div.className = "verse"

div.innerHTML = `

<h3>${v.book} ${v.chapter}:${v.verse}</h3>

<p>${highlight(v.text)}</p>

<button class="shareBtn">Copy</button>

`

div.querySelector("button").addEventListener("click",function(){

shareVerse(v)

})

results.appendChild(div)

})

}

function highlight(text){

const regex = new RegExp(`(${currentKeyword})`, "gi")

return text.replace(regex,"<mark>$1</mark>")

}

function shareVerse(v){

const text = `${v.book} ${v.chapter}:${v.verse} - ${v.text}`

navigator.clipboard.writeText(text)

alert("Verse copied to clipboard!")

}

const toggleBtn = document.getElementById("themeToggle")

toggleBtn.addEventListener("click", function(){

const body = document.body

if(body.classList.contains("dark")){

body.classList.remove("dark")
body.classList.add("light")
toggleBtn.innerText="🌙"

}else{

body.classList.remove("light")
body.classList.add("dark")
toggleBtn.innerText="☀️"

}

})