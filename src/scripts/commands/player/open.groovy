package commands.player

def thisRoom = source.getCurrentContainer();

def exit = thisRoom.getExit(argstr)

if (exit != null){
    exit.doOpen(source)
}