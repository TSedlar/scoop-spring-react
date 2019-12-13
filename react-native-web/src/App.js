import React from "react";

class App extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            packages: []
        };
    }

    async componentDidMount() {
        await this.searchPackages();
    }

    searchPackages = async () => {
        const query = this.refs.searchbar.value.toString();
        let endpoint = '/api/packages';
        if (query != null && query.length > 0) {
            endpoint = '/api/packages/search/' + query
        }
        const response = await fetch(endpoint);
        const data = await response.json();
        this.setState({packages: data});
    };

    navbar() {
        return (
            <nav className="uk-navbar uk-navbar-container" uk-sticky="sel-target: .uk-navbar-container">
                <div className="uk-navbar-left">
                    <div className="uk-navbar-item uk-logo">Scoop Packages</div>
                    <div className="uk-navbar-item">
                        <input ref="searchbar" className="uk-input uk-form-width-medium" type="text"
                               placeholder="Package"
                               maxLength="20" onChange={this.searchPackages}/>
                    </div>
                    <div className="uk-navbar-item">
                        <button className="uk-button uk-button-primary" onClick={this.searchPackages}>Submit</button>
                    </div>
                </div>
            </nav>
        );
    }

    makePackageRow(pkg) {
        return (
            <tr key={pkg["name"] + "-" + pkg["bucket"]}>
                <td>{pkg["name"]}</td>
                <td>{pkg["version"]}</td>
                <td>{pkg["bucket"]}</td>
                <td>{pkg["description"]}</td>
            </tr>
        );
    }

    render() {
        return (
            <div>
                {this.navbar()}
                <table className="uk-table uk-table-divider uk-table-striped">
                    <thead>
                    <tr>
                        <th>Package</th>
                        <th>Version</th>
                        <th>Bucket</th>
                        <th>Description</th>
                    </tr>
                    </thead>
                    <tbody>
                    {this.state.packages.map(pkg => this.makePackageRow(pkg))}
                    </tbody>
                </table>
            </div>
        )
    }
}

export default App;