import React, { useState } from "react";
import Container from "@mui/material/Container";
import Grid from "@mui/material/Grid";
import AppBar from "@mui/material/AppBar";
import Tabs from "@mui/material/Tabs";
import Tab from "@mui/material/Tab";
import MKBox from "components/MKBox";
import MyTabPanel from "components/MyTabPanel"

export default function Admin() {
    const [activeTab, setActiveTab] = useState(0);
    const handleTabType = (event, newValue) => setActiveTab(newValue);
    function a11yProps(index) {
        return {
            id: `simple-tab-${index}`,
            'aria-controls': `simple-tabpanel-${index}`,
        };
    }
    return (
        <MKBox bgColor="white" py={6}>
            <Container>
                <Grid container item justifyContent="center" xs={12} lg={4} mx="auto">
                    <AppBar position="static">
                        <Tabs value={activeTab} onChange={handleTabType}>
                            <Tab {...a11yProps(0)} label="My Profile" />
                            <Tab label="Dashboard" />
                        </Tabs>
                    </AppBar>
                    <MyTabPanel value={activeTab} index={0}>
                        Profile
                    </MyTabPanel>
                </Grid>
            </Container>
        </MKBox>
    )
}