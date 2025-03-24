# Building Docker Image

After creation a Dockerfile we need to build an image from it:
- go into the forder with docker file
- run:
`docker build . -t image_name`
- meaning of parameters used:
    + *.* &ndash; actual folder 
    + *-t image_name* &ndash; the name for created image (can be usefull later)

# Run existing image
Now we can create instance of the image, the instance is called a container:
- run
`docker run -it --rm --name container_name -v .:/workspace image_name`
- meaning of parameters used:
    + *-it* &ndash; starn interactive terminal to the container
    + *--rm* &ndash; remove container after closing it
    + *--name container_name* &ndash; a name for our container (can be usefull later)
    + *-v .:/workspace* &ndash; share the local folder with the container, the folder can be accessed inside the container via path /workspace
    + *image_name* &ndash; the name we want to instantiate

# Run a command in a container
If we have running container we can run a new comand inside it
- running:
`docker exec -it container_name bash`
- meaning of parameters used:
    + *-it* &ndash; run and add interactive terminal to it
    + *container_name* &ndash; name of the target container
    + *bash* &ndash; a command we want to run. In this case (bash) we will start a new bash shell to the container

