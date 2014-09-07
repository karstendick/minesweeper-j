package minesweeper_j.core;

import clojure.lang.RT;
import clojure.lang.Symbol;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.Game;

public class AndroidLauncher extends AndroidApplication {
	public void onCreate (android.os.Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
          RT.var("clojure.core", "require").invoke(Symbol.intern("minesweeper-j.core"));
		try {
			Game game = (Game) RT.var("minesweeper-j.core", "minesweeper-j").deref();
			initialize(game);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
