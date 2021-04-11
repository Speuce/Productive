Get [hugo](https://gohugo.io/getting-started/installing)  
You'll need a package manager with it. The website above walks you through it.  

Once you have hugo installed, you can run `hugo server -D` to host the server.  
The server hosts on `localhost:1313`

We designed this using an altered version of the https://github.com/hugo-toha/toha theme

### Editing

To edit the data on the website:
1. navigate to `data>en>sections`
2. open the yml file that is associated to the section you want to change
3. make your change

Note: The HTML gets populated using variables from the yml file.
Note2: I have enabled markdown on most of the text fields, but the markdown is finicky. It uses a tool called markdownify, if you need to google it.
Note3: if you want to see the HTML files, they are in `themes>toha>layouts>partials>sections`
    I made some custom partials, namely: vision.html, postmortem.html, video.html. I also heavily reworked about.html.
    Those are the 4 files that I know the site uses. I'd also speculate that it uses Home.html