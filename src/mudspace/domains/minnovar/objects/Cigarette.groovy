package domains.minnovar.objectsimport org.groovymud.object.registry.MudObjectAttendantimport org.groovymud.object.AbstractMudObjectimport org.groovymud.object.MudObjectimport org.groovymud.object.alive.Aliveimport org.groovymud.engine.event.messages.MessageEventimport org.groovymud.engine.event.EventScope/** * @author matt * */public class Cigarette extends AbstractMudObject{	def dragsLeft = 10	boolean lit = false		void initialise(){		setName("cigarette")		setDescription("A lovely little cancer stick. You might be able to smoke it." + 						(lit ? "It is currently alight and smoking away." : ""))		setShortNames(["cig", "cigarette", "fag", "smoke", "cancer stick"])		setWeight(0.1)			}		void doHeartBeat(){		if(lit){			MessageEvent msg = new MessageEvent();			msg.setScope(EventScope.ROOM_SCOPE);			msg.setSource(this.getCurrentContainer())			def rnd = Math.random() * 100 + 1			if(rnd > 20 && rnd < 30){				msg.setSourceMessage("Your cigaretter smokes quietly.")				msg.setScopeMessage(getCurrentContainer().getName()+"'s cigarette fills the room with smoke.")				dragsLeft--			}else if(rnd > 30 && rnd < 50){				msg.setSourceMessage("You cough violently!")				msg.setScopeMessage(getCurrentContainer().getName() + " hacks and coughs violently.")			}						if(msg.getSourceMessage() != null){				fireEvent(msg)			}		}		if(dragsLeft == 0){			lit = false;			MessageEvent msg = new MessageEvent();			msg.setScope(EventScope.ROOM_SCOPE);			msg.setSource(this.getCurrentContainer())			msg.setSourceMessage("You stub your cigarette out on the floor.")			msg.setScopeMessage("${getCurrentContainer().getName()} stubs a cigarette out on the floor.")			fireEvent(msg)			this.dest(true)		}	}		public boolean doCommand(String command, String argsAsString, MudObject performingObject) {		def stream = performingObject.getTerminalOutput()			if(command == 'drag' || command == 'smoke' || command == 'toke'){			if(argsAsString == null){				stream.writeln("You need to smoke something... try smoke cigarette?")				return true;			}			if(getShortNames().contains(argsAsString)){				if(lit){				    MessageEvent msg = new MessageEvent();					msg.setScope(EventScope.ROOM_SCOPE);					msg.setSource(this.getCurrentContainer())					msg.setSourceMessage("You take a long drag on the cigarette. Boy that feels good!")					msg.setScopeMessage("${getCurrentContainer().getName()} takes a long drag on the cigarette")					fireEvent(msg)										dragsLeft--;									}else{					stream.writeln('You need to light it first!')				}				return true;			}		}		if(command == 'light' && getShortNames().contains(argsAsString)){			if(!lit){				stream.writeln('You light up the cigarette!')				lit = true						}else{				stream.writeln('The cigaratte is already lit!')			}				return true;		}	}}