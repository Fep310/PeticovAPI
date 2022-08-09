package me.fep310.peticovapi.gui;

import org.bukkit.entity.Player;

public abstract class PagedGUI {

    private final PageBuilder.Page[] pages;

    public PagedGUI(int pagesAmount) {
        pages = new PageBuilder.Page[pagesAmount];
    }

    protected void addPage(int pageIndex, PageBuilder.Page page) {
        pages[pageIndex] = page;
    }

    public void open(int pageIndex, Player player) {
        pages[pageIndex].open(player);
    }
}
