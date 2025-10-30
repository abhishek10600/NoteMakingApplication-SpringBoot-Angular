import { Component, EventEmitter, Output } from "@angular/core";

@Component({
    selector: 'app-header',
    templateUrl: './header.component.html'
})

export class HeaderComponent{

    @Output() openCreateModal = new EventEmitter<void>();
    @Output() search = new EventEmitter<string>();

    searchKeyword: string = '';

    onCreateNewNote(){
        this.openCreateModal.emit();
    }

    onSearch() {
        this.search.emit(this.searchKeyword.trim());
    }
}