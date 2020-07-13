function init(contentDivId) {
    const topAppBar = document.getElementById('app-bar').MDCTopAppBar
    console.log("topAppBar is" + topAppBar);

    topAppBar.setScrollTarget(document.getElementById('main-content'));
    const drawer = document.querySelector('.mdc-drawer').MDCDrawer;
    topAppBar.listen('MDCTopAppBar:nav', () => {
        drawer.open = !drawer.open;
    });

    MainPage.render(contentDivId);
}