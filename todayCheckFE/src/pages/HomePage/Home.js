import React, { useState, useEffect } from 'react';
import styled from 'styled-components';
import axios from 'axios';
import HomeBox from '../../components/home/homeBox';
import { useNavigate } from 'react-router-dom';

const Home = () => {
  const [missions, setMissions] = useState();
  const navigate = useNavigate();

  useEffect(() => {
    axios
      .get('/mission')
      .then(response => {
        console.log(response);
        setMissions(response.data);
      })
      .catch(function (error) {
        console.log(error);
      });
  }, []);

  return (
    <RightContainer>
      {missions?.map((data, idx) => (
        <HomeBox
          key={idx}
          id={data?.id}
          thumbnail={data?.postPicture}
          adminName={data?.admin?.name}
          adminPicture={data?.admin?.imageBase}
          participants={data?.participants_number}
          postTitle={data?.postTitle}
          postContent={data?.content}
          startDate={data?.startDate}
          endDate={data?.endDate}
        />
      ))}
      <AddMisstion onClick={() => navigate('/createMission')}>
        미션 등록하기!
      </AddMisstion>
    </RightContainer>
  );
};

export default Home;

const RightContainer = styled.div`
  display: flex;
  align-items: flex-start;
  justify-content: flex-start;
  flex-wrap: wrap;
  height: 100vh;
  width: 100vw;
  padding-left: 15rem;
  background-color: #efefef;
  overflow-x: hidden;
  overflow-y: scroll;
  padding-bottom: 1rem;
`;

const AddMisstion = styled.div`
  position: fixed;
  display: flex;
  align-items: center;
  justify-content: center;
  bottom: 2rem;
  right: 2rem;
  width: 8rem;
  height: 3rem;
  border-radius: 1rem;
  background-color: #eb6440;
  color: white;
  cursor: pointer;
  transition: 0.3s all ease-in-out;

  :hover {
    transform: scale(1.1);
  }
`;
