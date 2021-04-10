public class DeclineBagPrompt {
	
	private boolean showPrompt = false;
	
	public DeclineBagPrompt() {
		
	}
	
	public void showPrompt() {
		this.showPrompt = true; 
	}
	
	public boolean getPrompt() {
		return showPrompt; 
	}
	
	public void attendentClosePrompt() {
		this.showPrompt = false;
	}
}
