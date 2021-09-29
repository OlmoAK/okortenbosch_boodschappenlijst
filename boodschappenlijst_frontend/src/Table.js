import React, { Component } from 'react';
import { AgGridReact } from 'ag-grid-react';
import 'ag-grid-community/dist/styles/ag-grid.css';
import 'ag-grid-community/dist/styles/ag-theme-alpine.css';

export class Table extends Component {
  constructor(props) {
    super(props);
    this.myRef = React.createRef();
    this.state = {
      columnDefs: [{
        headerName: "Item", field: "name", sortable: true, filter: true, editable: true, checkboxSelection: true
      }, {
        headerName: "Amount", field: "amount", sortable: true, filter: true, editable: true
      }, {
        headerName: "Shop", field: "shop", sortable: true, filter: true, editable: true
      }],
      rowData: [{
        name: "", amount: 0, bought: 0, shop: "", itemCode: 0
      }, {
        name: "", amount: 0, bought: 0, shop: "", itemCode: 1
      }, {
        name: "", amount: 0, bought: 0, shop: "", itemCode: 2
      }, {
        name: "", amount: 0, bought: 0, shop: "", itemCode: 3
      }, {
        name: "", amount: 0, bought: 0, shop: "", itemCode: 4
      }, {
        name: "", amount: 0, bought: 0, shop: "", itemCode: 5
      }, {
        name: "", amount: 0, bought: 0, shop: "", itemCode: 6
      }, {
        name: "", amount: 0, bought: 0, shop: "", itemCode: 7
      }, {
        name: "", amount: 0, bought: 0, shop: "", itemCode: 8
      }, {
        name: "", amount: 0, bought: 0, shop: "", itemCode: 9
      }]
    }
  }

  componentDidMount() {
    fetch('http://localhost:8080/boodschappenlijst/webapi/myresource').then(result => result.json()).then(rowData => this.setState({rowData})).catch(error => console.log(error));
  }

  // async componentDidMount() {
  //  const response = await fetch('http://localhost:8080/boodschappenlijst/webapi/items', {
  //    method: 'POST',
  //    headers: {
  //        'Accept': 'application/json',
  //        'Content-Type': 'application/json'
  //    },
  //    body: JSON.stringify({ Name: "Test", Password: "Test", Email: "", UserCode: 0})
  //  });

  //  if (response.ok) {
  //    this.state.rowData = await response.json();
  //  }
  // }
  
  async onButtonClick() {
    const selectedNodes = this.myRef.current.api.getSelectedNodes();
    const selectedData = selectedNodes.map(node => node.data);
    console.log(selectedData);
    console.log(JSON.stringify(selectedData));
    fetch('http://localhost:8080/boodschappenlijst/webapi/delete', {
      method: 'POST',
      headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(selectedData)
    }).then(result => result.json()).then(rowData => this.setState({rowData})).catch(error => console.log(error));
  }

  async Change(e) {
    if(e.newValue === undefined){
      if(e.data.name === undefined) {
        e.data.name = ""
      }
      if(e.data.shop === undefined) {
        e.data.shop = ""
      }
      if(e.data.amount === undefined || e.data.amount === "") {
        e.data.amount = 0
      }
      console.log(e.data)
    } else if (e.newValue === "" && e.oldValue === 0 && e.colDef.field === "amount") {
      if(e.data.name === undefined) {
        e.data.name = ""
      }
      if(e.data.shop === undefined) {
        e.data.shop = ""
      }
      if(e.data.amount === undefined || e.data.amount === "") {
        e.data.amount = 0
      }
      console.log(e.data)
    } else {
      if(e.data.name === undefined) {
        e.data.name = ""
      }
      if(e.data.shop === undefined) {
        e.data.shop = ""
      }
      if(e.data.amount === undefined || e.data.amount === "") {
        e.data.amount = 0
      }
      if(e.data.itemCode === 0) {
        console.log("Add");
        console.log(e.data);
        console.log(JSON.stringify(e.data));
        fetch('http://localhost:8080/boodschappenlijst/webapi/add', {
          method: 'POST',
          headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
          },
          body: JSON.stringify(e.data)
        }).then(result => result.json()).then(rowData => this.setState({rowData})).catch(error => console.log(error));
      } else {
        console.log("Edit");
        console.log(e.data);
        console.log(JSON.stringify(e.data));
        fetch('http://localhost:8080/boodschappenlijst/webapi/edit', {
          method: 'POST',
          headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
          },
          body: JSON.stringify(e.data)
        }).then(result => result.json()).then(rowData => this.setState({rowData})).catch(error => console.log(error));
      }
    }
  }
  
  render() {
    return (
      <div
        className="ag-theme-alpine"
        style={{
        height: '250px',
        width: '600px' }}
      >
        <AgGridReact
          columnDefs={this.state.columnDefs}
          rowData={this.state.rowData}
          rowSelection = "multiple"
          suppressRowClickSelection = {true}
          onCellValueChanged={(e) => this.Change(e)}
          ref = {this.myRef}>
        </AgGridReact>
        <button onClick={() => this.onButtonClick()}>Delete selected items</button>
      </div>
    );
  }
}

export default Table