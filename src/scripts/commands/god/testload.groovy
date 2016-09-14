/**
 * 
 */
package commands.god

int y = Integer.parseInt(args[1])

for(int x = 0; x < y; x++){
	source.getCurrentContainer().addItem(source.load(args[0]))
}

