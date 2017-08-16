package musicshop.domain.item;

public class Guitar implements MusicItem {

	private GuitarMark mark;
	private String name;
	
	public Guitar(final GuitarMark mark, final String name) {
		this.mark = mark;
		this.name = name;
	}

	public GuitarMark getMark() {
		return mark;
	}

	public void setMark(GuitarMark mark) {
		this.mark = mark;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
