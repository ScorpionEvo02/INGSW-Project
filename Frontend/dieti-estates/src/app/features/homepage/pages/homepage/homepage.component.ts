import { Component } from '@angular/core';
import { HeaderComponent } from '../../../../base/header/header.component';
import { SearchComponent } from '../../components/search/search.component';
import { HistoryComponent } from '../../components/history/history.component';
import { ItalyComponent } from '../../components/italy/italy.component';

@Component({
    selector: 'homepage-component',
    templateUrl: './homepage.component.html',
    styleUrls: [],
    imports: [ HeaderComponent, SearchComponent, 
        HistoryComponent, ItalyComponent ]
})

export class HomepageComponent {

}